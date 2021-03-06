package reactivecircus.flowbinding.material

import androidx.test.filters.LargeTest
import androidx.test.internal.runner.junit4.statement.UiThreadStatement.runOnUiThread
import com.google.android.material.button.MaterialButton
import com.google.android.material.button.MaterialButtonToggleGroup
import org.amshove.kluent.shouldEqual
import org.junit.Test
import reactivecircus.blueprint.testing.action.clickView
import reactivecircus.flowbinding.material.fixtures.MaterialFragment1
import reactivecircus.flowbinding.material.test.R
import reactivecircus.flowbinding.testing.FlowRecorder
import reactivecircus.flowbinding.testing.launchTest
import reactivecircus.flowbinding.testing.recordWith

@LargeTest
class MaterialButtonCheckedChangedFlowTest {

    @Test
    fun materialButtonCheckedChanges_click() {
        launchTest<MaterialFragment1> {
            val recorder = FlowRecorder<Boolean>(testScope)
            getViewById<MaterialButton>(R.id.button1).checkedChanges().recordWith(recorder)

            recorder.assertNoMoreValues()

            clickView(R.id.button1)
            recorder.takeValue() shouldEqual true
            recorder.assertNoMoreValues()

            clickView(R.id.button2)
            recorder.takeValue() shouldEqual false
            recorder.assertNoMoreValues()

            cancelTestScope()

            clickView(R.id.button1)
            recorder.assertNoMoreValues()
        }
    }

    @Test
    fun materialButtonCheckedChanges_programmatic() {
        launchTest<MaterialFragment1> {
            val recorder = FlowRecorder<Boolean>(testScope)
            val buttonGroup = getViewById<MaterialButtonToggleGroup>(R.id.buttonToggleGroup)
            val button = getViewById<MaterialButton>(R.id.button1)
            button.checkedChanges().recordWith(recorder)

            recorder.assertNoMoreValues()

            runOnUiThread { buttonGroup.check(R.id.button1) }
            recorder.takeValue() shouldEqual true
            recorder.assertNoMoreValues()

            runOnUiThread { buttonGroup.uncheck(R.id.button1) }
            recorder.takeValue() shouldEqual false
            recorder.assertNoMoreValues()

            cancelTestScope()

            runOnUiThread { buttonGroup.check(R.id.button1) }
            recorder.assertNoMoreValues()
        }
    }
}
