// This file was autogenerated by some hot garbage in the `uniffi` crate.
// Trust me, you don't want to mess with it!

@file:Suppress("NAME_SHADOWING")

package uniffi.vehicles;

// Common helper code.
//
// Ideally this would live in a separate .kt file where it can be unittested etc
// in isolation, and perhaps even published as a re-useable package.
//
// However, it's important that the details of how this helper code works (e.g. the
// way that different builtin types are passed across the FFI) exactly match what's
// expected by the Rust code on the other side of the interface. In practice right
// now that means coming from the exact some version of `uniffi` that was used to
// compile the Rust component. The easiest way to ensure this is to bundle the Kotlin
// helpers directly inline like we're doing here.

import com.sun.jna.Library
import com.sun.jna.IntegerType
import com.sun.jna.Native
import com.sun.jna.Pointer
import com.sun.jna.Structure
import com.sun.jna.Callback
import com.sun.jna.ptr.*
import java.nio.ByteBuffer
import java.nio.ByteOrder
import java.nio.CharBuffer
import java.nio.charset.CodingErrorAction
import java.util.concurrent.ConcurrentHashMap

// This is a helper for safely working with byte buffers returned from the Rust code.
// A rust-owned buffer is represented by its capacity, its current length, and a
// pointer to the underlying data.

@Structure.FieldOrder("capacity", "len", "data")
open class RustBuffer : Structure() {
    @JvmField var capacity: Int = 0
    @JvmField var len: Int = 0
    @JvmField var data: Pointer? = null

    class ByValue: RustBuffer(), Structure.ByValue
    class ByReference: RustBuffer(), Structure.ByReference

    companion object {
        internal fun alloc(size: Int = 0) = rustCall() { status ->
            _UniFFILib.INSTANCE.ffi_vehicles_rustbuffer_alloc(size, status)
        }.also {
            if(it.data == null) {
               throw RuntimeException("RustBuffer.alloc() returned null data pointer (size=${size})")
           }
        }

        internal fun create(capacity: Int, len: Int, data: Pointer?): RustBuffer.ByValue {
            var buf = RustBuffer.ByValue()
            buf.capacity = capacity
            buf.len = len
            buf.data = data
            return buf
        }

        internal fun free(buf: RustBuffer.ByValue) = rustCall() { status ->
            _UniFFILib.INSTANCE.ffi_vehicles_rustbuffer_free(buf, status)
        }
    }

    @Suppress("TooGenericExceptionThrown")
    fun asByteBuffer() =
        this.data?.getByteBuffer(0, this.len.toLong())?.also {
            it.order(ByteOrder.BIG_ENDIAN)
        }
}

/**
 * The equivalent of the `*mut RustBuffer` type.
 * Required for callbacks taking in an out pointer.
 *
 * Size is the sum of all values in the struct.
 */
class RustBufferByReference : ByReference(16) {
    /**
     * Set the pointed-to `RustBuffer` to the given value.
     */
    fun setValue(value: RustBuffer.ByValue) {
        // NOTE: The offsets are as they are in the C-like struct.
        val pointer = getPointer()
        pointer.setInt(0, value.capacity)
        pointer.setInt(4, value.len)
        pointer.setPointer(8, value.data)
    }

    /**
     * Get a `RustBuffer.ByValue` from this reference.
     */
    fun getValue(): RustBuffer.ByValue {
        val pointer = getPointer()
        val value = RustBuffer.ByValue()
        value.writeField("capacity", pointer.getInt(0))
        value.writeField("len", pointer.getInt(4))
        value.writeField("data", pointer.getPointer(8))

        return value
    }
}

// This is a helper for safely passing byte references into the rust code.
// It's not actually used at the moment, because there aren't many things that you
// can take a direct pointer to in the JVM, and if we're going to copy something
// then we might as well copy it into a `RustBuffer`. But it's here for API
// completeness.

@Structure.FieldOrder("len", "data")
open class ForeignBytes : Structure() {
    @JvmField var len: Int = 0
    @JvmField var data: Pointer? = null

    class ByValue : ForeignBytes(), Structure.ByValue
}
// The FfiConverter interface handles converter types to and from the FFI
//
// All implementing objects should be public to support external types.  When a
// type is external we need to import it's FfiConverter.
public interface FfiConverter<KotlinType, FfiType> {
    // Convert an FFI type to a Kotlin type
    fun lift(value: FfiType): KotlinType

    // Convert an Kotlin type to an FFI type
    fun lower(value: KotlinType): FfiType

    // Read a Kotlin type from a `ByteBuffer`
    fun read(buf: ByteBuffer): KotlinType

    // Calculate bytes to allocate when creating a `RustBuffer`
    //
    // This must return at least as many bytes as the write() function will
    // write. It can return more bytes than needed, for example when writing
    // Strings we can't know the exact bytes needed until we the UTF-8
    // encoding, so we pessimistically allocate the largest size possible (3
    // bytes per codepoint).  Allocating extra bytes is not really a big deal
    // because the `RustBuffer` is short-lived.
    fun allocationSize(value: KotlinType): Int

    // Write a Kotlin type to a `ByteBuffer`
    fun write(value: KotlinType, buf: ByteBuffer)

    // Lower a value into a `RustBuffer`
    //
    // This method lowers a value into a `RustBuffer` rather than the normal
    // FfiType.  It's used by the callback interface code.  Callback interface
    // returns are always serialized into a `RustBuffer` regardless of their
    // normal FFI type.
    fun lowerIntoRustBuffer(value: KotlinType): RustBuffer.ByValue {
        val rbuf = RustBuffer.alloc(allocationSize(value))
        try {
            val bbuf = rbuf.data!!.getByteBuffer(0, rbuf.capacity.toLong()).also {
                it.order(ByteOrder.BIG_ENDIAN)
            }
            write(value, bbuf)
            rbuf.writeField("len", bbuf.position())
            return rbuf
        } catch (e: Throwable) {
            RustBuffer.free(rbuf)
            throw e
        }
    }

    // Lift a value from a `RustBuffer`.
    //
    // This here mostly because of the symmetry with `lowerIntoRustBuffer()`.
    // It's currently only used by the `FfiConverterRustBuffer` class below.
    fun liftFromRustBuffer(rbuf: RustBuffer.ByValue): KotlinType {
        val byteBuf = rbuf.asByteBuffer()!!
        try {
           val item = read(byteBuf)
           if (byteBuf.hasRemaining()) {
               throw RuntimeException("junk remaining in buffer after lifting, something is very wrong!!")
           }
           return item
        } finally {
            RustBuffer.free(rbuf)
        }
    }
}

// FfiConverter that uses `RustBuffer` as the FfiType
public interface FfiConverterRustBuffer<KotlinType>: FfiConverter<KotlinType, RustBuffer.ByValue> {
    override fun lift(value: RustBuffer.ByValue) = liftFromRustBuffer(value)
    override fun lower(value: KotlinType) = lowerIntoRustBuffer(value)
}
// A handful of classes and functions to support the generated data structures.
// This would be a good candidate for isolating in its own ffi-support lib.
// Error runtime.
@Structure.FieldOrder("code", "error_buf")
internal open class RustCallStatus : Structure() {
    @JvmField var code: Byte = 0
    @JvmField var error_buf: RustBuffer.ByValue = RustBuffer.ByValue()

    class ByValue: RustCallStatus(), Structure.ByValue

    fun isSuccess(): Boolean {
        return code == 0.toByte()
    }

    fun isError(): Boolean {
        return code == 1.toByte()
    }

    fun isPanic(): Boolean {
        return code == 2.toByte()
    }
}

class InternalException(message: String) : Exception(message)

// Each top-level error class has a companion object that can lift the error from the call status's rust buffer
interface CallStatusErrorHandler<E> {
    fun lift(error_buf: RustBuffer.ByValue): E;
}

// Helpers for calling Rust
// In practice we usually need to be synchronized to call this safely, so it doesn't
// synchronize itself

// Call a rust function that returns a Result<>.  Pass in the Error class companion that corresponds to the Err
private inline fun <U, E: Exception> rustCallWithError(errorHandler: CallStatusErrorHandler<E>, callback: (RustCallStatus) -> U): U {
    var status = RustCallStatus();
    val return_value = callback(status)
    checkCallStatus(errorHandler, status)
    return return_value
}

// Check RustCallStatus and throw an error if the call wasn't successful
private fun<E: Exception> checkCallStatus(errorHandler: CallStatusErrorHandler<E>, status: RustCallStatus) {
    if (status.isSuccess()) {
        return
    } else if (status.isError()) {
        throw errorHandler.lift(status.error_buf)
    } else if (status.isPanic()) {
        // when the rust code sees a panic, it tries to construct a rustbuffer
        // with the message.  but if that code panics, then it just sends back
        // an empty buffer.
        if (status.error_buf.len > 0) {
            throw InternalException(FfiConverterString.lift(status.error_buf))
        } else {
            throw InternalException("Rust panic")
        }
    } else {
        throw InternalException("Unknown rust call status: $status.code")
    }
}

// CallStatusErrorHandler implementation for times when we don't expect a CALL_ERROR
object NullCallStatusErrorHandler: CallStatusErrorHandler<InternalException> {
    override fun lift(error_buf: RustBuffer.ByValue): InternalException {
        RustBuffer.free(error_buf)
        return InternalException("Unexpected CALL_ERROR")
    }
}

// Call a rust function that returns a plain value
private inline fun <U> rustCall(callback: (RustCallStatus) -> U): U {
    return rustCallWithError(NullCallStatusErrorHandler, callback);
}

// IntegerType that matches Rust's `usize` / C's `size_t`
public class USize(value: Long = 0) : IntegerType(Native.SIZE_T_SIZE, value, true) {
    // This is needed to fill in the gaps of IntegerType's implementation of Number for Kotlin.
    override fun toByte() = toInt().toByte()
    // Needed until https://youtrack.jetbrains.com/issue/KT-47902 is fixed.
    @Deprecated("`toInt().toChar()` is deprecated")
    override fun toChar() = toInt().toChar()
    override fun toShort() = toInt().toShort()

    fun writeToBuffer(buf: ByteBuffer) {
        // Make sure we always write usize integers using native byte-order, since they may be
        // casted to pointer values
        buf.order(ByteOrder.nativeOrder())
        try {
            when (Native.SIZE_T_SIZE) {
                4 -> buf.putInt(toInt())
                8 -> buf.putLong(toLong())
                else -> throw RuntimeException("Invalid SIZE_T_SIZE: ${Native.SIZE_T_SIZE}")
            }
        } finally {
            buf.order(ByteOrder.BIG_ENDIAN)
        }
    }

    companion object {
        val size: Int
            get() = Native.SIZE_T_SIZE

        fun readFromBuffer(buf: ByteBuffer) : USize {
            // Make sure we always read usize integers using native byte-order, since they may be
            // casted from pointer values
            buf.order(ByteOrder.nativeOrder())
            try {
                return when (Native.SIZE_T_SIZE) {
                    4 -> USize(buf.getInt().toLong())
                    8 -> USize(buf.getLong())
                    else -> throw RuntimeException("Invalid SIZE_T_SIZE: ${Native.SIZE_T_SIZE}")
                }
            } finally {
                buf.order(ByteOrder.BIG_ENDIAN)
            }
        }
    }
}


// Map handles to objects
//
// This is used when the Rust code expects an opaque pointer to represent some foreign object.
// Normally we would pass a pointer to the object, but JNA doesn't support getting a pointer from an
// object reference , nor does it support leaking a reference to Rust.
//
// Instead, this class maps USize values to objects so that we can pass a pointer-sized type to
// Rust when it needs an opaque pointer.
//
// TODO: refactor callbacks to use this class
internal class UniFfiHandleMap<T: Any> {
    private val map = ConcurrentHashMap<USize, T>()
    // Use AtomicInteger for our counter, since we may be on a 32-bit system.  4 billion possible
    // values seems like enough. If somehow we generate 4 billion handles, then this will wrap
    // around back to zero and we can assume the first handle generated will have been dropped by
    // then.
    private val counter = java.util.concurrent.atomic.AtomicInteger(0)

    val size: Int
        get() = map.size

    fun insert(obj: T): USize {
        val handle = USize(counter.getAndAdd(1).toLong())
        map.put(handle, obj)
        return handle
    }

    fun get(handle: USize): T? {
        return map.get(handle)
    }

    fun remove(handle: USize): T? {
        return map.remove(handle)
    }
}

// FFI type for Rust future continuations
internal interface UniFffiRustFutureContinuationCallbackType : com.sun.jna.Callback {
    fun callback(continuationHandle: USize, pollResult: Short);
}

// Contains loading, initialization code,
// and the FFI Function declarations in a com.sun.jna.Library.
@Synchronized
private fun findLibraryName(componentName: String): String {
    val libOverride = System.getProperty("uniffi.component.$componentName.libraryOverride")
    if (libOverride != null) {
        return libOverride
    }
    return "vehicles"
}

private inline fun <reified Lib : Library> loadIndirect(
    componentName: String
): Lib {
    return Native.load<Lib>(findLibraryName(componentName), Lib::class.java)
}

// A JNA Library to expose the extern-C FFI definitions.
// This is an implementation detail which will be called internally by the public API.

internal interface _UniFFILib : Library {
    companion object {
        internal val INSTANCE: _UniFFILib by lazy {
            loadIndirect<_UniFFILib>(componentName = "vehicles")
            .also { lib: _UniFFILib ->
                uniffiCheckContractApiVersion(lib)
                uniffiCheckApiChecksums(lib)
                }
        }
    }

    fun uniffi_vehicles_fn_func_makes(`id`: RustBuffer.ByValue,_uniffi_out_err: RustCallStatus, 
    ): RustBuffer.ByValue
    fun uniffi_vehicles_fn_func_manufacturers(_uniffi_out_err: RustCallStatus, 
    ): RustBuffer.ByValue
    fun ffi_vehicles_rustbuffer_alloc(`size`: Int,_uniffi_out_err: RustCallStatus, 
    ): RustBuffer.ByValue
    fun ffi_vehicles_rustbuffer_from_bytes(`bytes`: ForeignBytes.ByValue,_uniffi_out_err: RustCallStatus, 
    ): RustBuffer.ByValue
    fun ffi_vehicles_rustbuffer_free(`buf`: RustBuffer.ByValue,_uniffi_out_err: RustCallStatus, 
    ): Unit
    fun ffi_vehicles_rustbuffer_reserve(`buf`: RustBuffer.ByValue,`additional`: Int,_uniffi_out_err: RustCallStatus, 
    ): RustBuffer.ByValue
    fun ffi_vehicles_rust_future_continuation_callback_set(`callback`: UniFffiRustFutureContinuationCallbackType,
    ): Unit
    fun ffi_vehicles_rust_future_poll_u8(`handle`: Pointer,`uniffiCallback`: USize,
    ): Unit
    fun ffi_vehicles_rust_future_cancel_u8(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_free_u8(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_complete_u8(`handle`: Pointer,_uniffi_out_err: RustCallStatus, 
    ): Byte
    fun ffi_vehicles_rust_future_poll_i8(`handle`: Pointer,`uniffiCallback`: USize,
    ): Unit
    fun ffi_vehicles_rust_future_cancel_i8(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_free_i8(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_complete_i8(`handle`: Pointer,_uniffi_out_err: RustCallStatus, 
    ): Byte
    fun ffi_vehicles_rust_future_poll_u16(`handle`: Pointer,`uniffiCallback`: USize,
    ): Unit
    fun ffi_vehicles_rust_future_cancel_u16(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_free_u16(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_complete_u16(`handle`: Pointer,_uniffi_out_err: RustCallStatus, 
    ): Short
    fun ffi_vehicles_rust_future_poll_i16(`handle`: Pointer,`uniffiCallback`: USize,
    ): Unit
    fun ffi_vehicles_rust_future_cancel_i16(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_free_i16(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_complete_i16(`handle`: Pointer,_uniffi_out_err: RustCallStatus, 
    ): Short
    fun ffi_vehicles_rust_future_poll_u32(`handle`: Pointer,`uniffiCallback`: USize,
    ): Unit
    fun ffi_vehicles_rust_future_cancel_u32(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_free_u32(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_complete_u32(`handle`: Pointer,_uniffi_out_err: RustCallStatus, 
    ): Int
    fun ffi_vehicles_rust_future_poll_i32(`handle`: Pointer,`uniffiCallback`: USize,
    ): Unit
    fun ffi_vehicles_rust_future_cancel_i32(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_free_i32(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_complete_i32(`handle`: Pointer,_uniffi_out_err: RustCallStatus, 
    ): Int
    fun ffi_vehicles_rust_future_poll_u64(`handle`: Pointer,`uniffiCallback`: USize,
    ): Unit
    fun ffi_vehicles_rust_future_cancel_u64(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_free_u64(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_complete_u64(`handle`: Pointer,_uniffi_out_err: RustCallStatus, 
    ): Long
    fun ffi_vehicles_rust_future_poll_i64(`handle`: Pointer,`uniffiCallback`: USize,
    ): Unit
    fun ffi_vehicles_rust_future_cancel_i64(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_free_i64(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_complete_i64(`handle`: Pointer,_uniffi_out_err: RustCallStatus, 
    ): Long
    fun ffi_vehicles_rust_future_poll_f32(`handle`: Pointer,`uniffiCallback`: USize,
    ): Unit
    fun ffi_vehicles_rust_future_cancel_f32(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_free_f32(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_complete_f32(`handle`: Pointer,_uniffi_out_err: RustCallStatus, 
    ): Float
    fun ffi_vehicles_rust_future_poll_f64(`handle`: Pointer,`uniffiCallback`: USize,
    ): Unit
    fun ffi_vehicles_rust_future_cancel_f64(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_free_f64(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_complete_f64(`handle`: Pointer,_uniffi_out_err: RustCallStatus, 
    ): Double
    fun ffi_vehicles_rust_future_poll_pointer(`handle`: Pointer,`uniffiCallback`: USize,
    ): Unit
    fun ffi_vehicles_rust_future_cancel_pointer(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_free_pointer(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_complete_pointer(`handle`: Pointer,_uniffi_out_err: RustCallStatus, 
    ): Pointer
    fun ffi_vehicles_rust_future_poll_rust_buffer(`handle`: Pointer,`uniffiCallback`: USize,
    ): Unit
    fun ffi_vehicles_rust_future_cancel_rust_buffer(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_free_rust_buffer(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_complete_rust_buffer(`handle`: Pointer,_uniffi_out_err: RustCallStatus, 
    ): RustBuffer.ByValue
    fun ffi_vehicles_rust_future_poll_void(`handle`: Pointer,`uniffiCallback`: USize,
    ): Unit
    fun ffi_vehicles_rust_future_cancel_void(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_free_void(`handle`: Pointer,
    ): Unit
    fun ffi_vehicles_rust_future_complete_void(`handle`: Pointer,_uniffi_out_err: RustCallStatus, 
    ): Unit
    fun uniffi_vehicles_checksum_func_makes(
    ): Short
    fun uniffi_vehicles_checksum_func_manufacturers(
    ): Short
    fun ffi_vehicles_uniffi_contract_version(
    ): Int
    
}

private fun uniffiCheckContractApiVersion(lib: _UniFFILib) {
    // Get the bindings contract version from our ComponentInterface
    val bindings_contract_version = 24
    // Get the scaffolding contract version by calling the into the dylib
    val scaffolding_contract_version = lib.ffi_vehicles_uniffi_contract_version()
    if (bindings_contract_version != scaffolding_contract_version) {
        throw RuntimeException("UniFFI contract version mismatch: try cleaning and rebuilding your project")
    }
}

@Suppress("UNUSED_PARAMETER")
private fun uniffiCheckApiChecksums(lib: _UniFFILib) {
    if (lib.uniffi_vehicles_checksum_func_makes() != 36574.toShort()) {
        throw RuntimeException("UniFFI API checksum mismatch: try cleaning and rebuilding your project")
    }
    if (lib.uniffi_vehicles_checksum_func_manufacturers() != 26746.toShort()) {
        throw RuntimeException("UniFFI API checksum mismatch: try cleaning and rebuilding your project")
    }
}

// Async support

// Public interface members begin here.


public object FfiConverterString: FfiConverter<String, RustBuffer.ByValue> {
    // Note: we don't inherit from FfiConverterRustBuffer, because we use a
    // special encoding when lowering/lifting.  We can use `RustBuffer.len` to
    // store our length and avoid writing it out to the buffer.
    override fun lift(value: RustBuffer.ByValue): String {
        try {
            val byteArr = ByteArray(value.len)
            value.asByteBuffer()!!.get(byteArr)
            return byteArr.toString(Charsets.UTF_8)
        } finally {
            RustBuffer.free(value)
        }
    }

    override fun read(buf: ByteBuffer): String {
        val len = buf.getInt()
        val byteArr = ByteArray(len)
        buf.get(byteArr)
        return byteArr.toString(Charsets.UTF_8)
    }

    fun toUtf8(value: String): ByteBuffer {
        // Make sure we don't have invalid UTF-16, check for lone surrogates.
        return Charsets.UTF_8.newEncoder().run {
            onMalformedInput(CodingErrorAction.REPORT)
            encode(CharBuffer.wrap(value))
        }
    }

    override fun lower(value: String): RustBuffer.ByValue {
        val byteBuf = toUtf8(value)
        // Ideally we'd pass these bytes to `ffi_bytebuffer_from_bytes`, but doing so would require us
        // to copy them into a JNA `Memory`. So we might as well directly copy them into a `RustBuffer`.
        val rbuf = RustBuffer.alloc(byteBuf.limit())
        rbuf.asByteBuffer()!!.put(byteBuf)
        return rbuf
    }

    // We aren't sure exactly how many bytes our string will be once it's UTF-8
    // encoded.  Allocate 3 bytes per UTF-16 code unit which will always be
    // enough.
    override fun allocationSize(value: String): Int {
        val sizeForLength = 4
        val sizeForString = value.length * 3
        return sizeForLength + sizeForString
    }

    override fun write(value: String, buf: ByteBuffer) {
        val byteBuf = toUtf8(value)
        buf.putInt(byteBuf.limit())
        buf.put(byteBuf)
    }
}




data class Make (
    var `name`: String
) {
    
    companion object
}

public object FfiConverterTypeMake: FfiConverterRustBuffer<Make> {
    override fun read(buf: ByteBuffer): Make {
        return Make(
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: Make) = (
            FfiConverterString.allocationSize(value.`name`)
    )

    override fun write(value: Make, buf: ByteBuffer) {
            FfiConverterString.write(value.`name`, buf)
    }
}




data class Manufacturer (
    var `id`: String, 
    var `name`: String
) {
    
    companion object
}

public object FfiConverterTypeManufacturer: FfiConverterRustBuffer<Manufacturer> {
    override fun read(buf: ByteBuffer): Manufacturer {
        return Manufacturer(
            FfiConverterString.read(buf),
            FfiConverterString.read(buf),
        )
    }

    override fun allocationSize(value: Manufacturer) = (
            FfiConverterString.allocationSize(value.`id`) +
            FfiConverterString.allocationSize(value.`name`)
    )

    override fun write(value: Manufacturer, buf: ByteBuffer) {
            FfiConverterString.write(value.`id`, buf)
            FfiConverterString.write(value.`name`, buf)
    }
}





sealed class VehiclesException: Exception() {
    // Each variant is a nested class
    
    class Fetch(
        ) : VehiclesException() {
        override val message
            get() = ""
    }
    

    companion object ErrorHandler : CallStatusErrorHandler<VehiclesException> {
        override fun lift(error_buf: RustBuffer.ByValue): VehiclesException = FfiConverterTypeVehiclesError.lift(error_buf)
    }

    
}

public object FfiConverterTypeVehiclesError : FfiConverterRustBuffer<VehiclesException> {
    override fun read(buf: ByteBuffer): VehiclesException {
        

        return when(buf.getInt()) {
            1 -> VehiclesException.Fetch()
            else -> throw RuntimeException("invalid error enum value, something is very wrong!!")
        }
    }

    override fun allocationSize(value: VehiclesException): Int {
        return when(value) {
            is VehiclesException.Fetch -> (
                // Add the size for the Int that specifies the variant plus the size needed for all fields
                4
            )
        }
    }

    override fun write(value: VehiclesException, buf: ByteBuffer) {
        when(value) {
            is VehiclesException.Fetch -> {
                buf.putInt(1)
                Unit
            }
        }.let { /* this makes the `when` an expression, which ensures it is exhaustive */ }
    }

}




public object FfiConverterSequenceTypeMake: FfiConverterRustBuffer<List<Make>> {
    override fun read(buf: ByteBuffer): List<Make> {
        val len = buf.getInt()
        return List<Make>(len) {
            FfiConverterTypeMake.read(buf)
        }
    }

    override fun allocationSize(value: List<Make>): Int {
        val sizeForLength = 4
        val sizeForItems = value.map { FfiConverterTypeMake.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<Make>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.forEach {
            FfiConverterTypeMake.write(it, buf)
        }
    }
}




public object FfiConverterSequenceTypeManufacturer: FfiConverterRustBuffer<List<Manufacturer>> {
    override fun read(buf: ByteBuffer): List<Manufacturer> {
        val len = buf.getInt()
        return List<Manufacturer>(len) {
            FfiConverterTypeManufacturer.read(buf)
        }
    }

    override fun allocationSize(value: List<Manufacturer>): Int {
        val sizeForLength = 4
        val sizeForItems = value.map { FfiConverterTypeManufacturer.allocationSize(it) }.sum()
        return sizeForLength + sizeForItems
    }

    override fun write(value: List<Manufacturer>, buf: ByteBuffer) {
        buf.putInt(value.size)
        value.forEach {
            FfiConverterTypeManufacturer.write(it, buf)
        }
    }
}
@Throws(VehiclesException::class)

fun `makes`(`id`: String): List<Make> {
    return FfiConverterSequenceTypeMake.lift(
    rustCallWithError(VehiclesException) { _status ->
    _UniFFILib.INSTANCE.uniffi_vehicles_fn_func_makes(FfiConverterString.lower(`id`),_status)
})
}

@Throws(VehiclesException::class)

fun `manufacturers`(): List<Manufacturer> {
    return FfiConverterSequenceTypeManufacturer.lift(
    rustCallWithError(VehiclesException) { _status ->
    _UniFFILib.INSTANCE.uniffi_vehicles_fn_func_manufacturers(_status)
})
}


