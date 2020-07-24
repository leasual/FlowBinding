package reactivecircus.flowbinding.material

import androidx.annotation.CheckResult
import com.google.android.material.datepicker.MaterialDatePicker
import com.google.android.material.timepicker.MaterialTimePicker
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import reactivecircus.flowbinding.common.checkMainThread
import reactivecircus.flowbinding.common.safeOffer

/**
 * Create a [Flow] of time set events on the [MaterialTimePicker] instance
 * when the user is done setting a new time and the dialog has closed.
 *
 * Note: Created flow keeps a strong reference to the [MaterialTimePicker] instance
 * until the coroutine that launched the flow collector is cancelled.
 *
 * Example of usage:
 *
 * ```
 * timePicker.timeSets()
 *     .onEach { event ->
 *          // handle time set event
 *     }
 *     .launchIn(uiScope)
 * ```
 */
@CheckResult
@OptIn(ExperimentalCoroutinesApi::class)
fun MaterialTimePicker.timeSets(): Flow<MaterialTimePickerTimeSetEvent> = callbackFlow {
    checkMainThread()
    val listener = MaterialTimePicker.OnTimeSetListener {
        safeOffer(
            MaterialTimePickerTimeSetEvent(
                hour = it.hour,
                minute = it.minute,
                inputMode = it.inputMode
            )
        )
    }
    setListener(listener)
    awaitClose { setListener(null) }
}.conflate()

class MaterialTimePickerTimeSetEvent(
    val hour: Int,
    val minute: Int,
    @MaterialDatePicker.InputMode
    val inputMode: Int
)
