package reactivecircus.flowbinding.material

import androidx.fragment.app.FragmentActivity
import androidx.test.filters.LargeTest
import com.google.android.material.timepicker.MaterialTimePicker
import org.junit.Test
import reactivecircus.blueprint.testing.currentActivity
import reactivecircus.flowbinding.material.fixtures.MaterialFragment1
import reactivecircus.flowbinding.testing.FlowRecorder
import reactivecircus.flowbinding.testing.clickOkButtonOnTimePicker
import reactivecircus.flowbinding.testing.launchTest
import reactivecircus.flowbinding.testing.recordWith

@LargeTest
class MaterialTimerPickerTimeSetFlowTest {

    @Test
    fun materialTimePickerTimeSets() {
        launchTest<MaterialFragment1> {
            val recorder = FlowRecorder<MaterialTimePickerTimeSetEvent>(testScope)
            val picker = MaterialTimePicker.newInstance()
            picker.timeSets().recordWith(recorder)

            recorder.assertNoMoreValues()

            picker.show((currentActivity() as FragmentActivity).supportFragmentManager, toString())
            clickOkButtonOnTimePicker()
            val event1 = recorder.takeValue()
            // TODO

            cancelTestScope()
            recorder.clearValues()

            picker.show((currentActivity() as FragmentActivity).supportFragmentManager, toString())
            clickOkButtonOnTimePicker()
            recorder.assertNoMoreValues()
        }
    }
}
