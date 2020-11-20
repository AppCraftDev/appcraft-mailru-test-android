//package ru.mailru_test.app.adapter.controller
//
//import android.content.Context
//import com.airbnb.epoxy.TypedEpoxyController
//import ru.mailru_test.R
//import ru.mailru_test.domain.model.TaskPreview
//import ru.mailru_test.domain.model.TaskStep
//
//class TasksPreviewController(val context: Context, val callback: OnClickListener) : TypedEpoxyController<List<TaskListCalendar>>() {
//
//    private val spaceMonth = context.resources.getDimensionPixelOffset(R.dimen.baseline_grid_medium)
//    private val spaceDay = context.resources.getDimensionPixelOffset(R.dimen.height_divider)
//    private val spaceBotDay = context.resources.getDimensionPixelOffset(R.dimen.baseline_grid)
//    private val spaceTask = context.resources.getDimensionPixelOffset(R.dimen.baseline_grid_small)
//
//    override fun buildModels(data: List<TaskListCalendar>) {
//        var prevItemMonth = false
//        data.forEach { item ->
//            when (item) {
//                is TaskListCalendar.Month -> {
//                    addSpace("space" + item.date.time, spaceMonth)
//                    monthItem {
//                        id("moth" + item.date.time)
//                        data(item)
//                    }
//                }
//                is TaskListCalendar.Day -> {
//                    addSpace("spaceTop" + item.date.time, if (prevItemMonth) spaceDay else spaceBotDay)
//                    dayItem {
//                        id("day" + item.date.time)
//                        data(item)
//                    }
//                    addSpace("spaceBot" + item.date.time, spaceBotDay)
//                }
//                is TaskListCalendar.TaskCalendar -> {
//                    addSpace("spaceTop" + item.task.id, spaceTask)
//                    taskPreviewItem {
//                        id(item.task.id)
//                        data(item)
//                        clickOnDial { callback.onStep(item.task.id, TaskStep.DEAL) }
//                        clickOnDelivery { callback.onStep(item.task.id, TaskStep.INSPECTION) }
//                        clickOnDoc { callback.onStep(item.task.id, TaskStep.DOC) }
//                        clickListener { callback.onTaskDetail(item.task) }
//                    }
//                    addSpace("spaceBot" + item.task.id, spaceTask)
//                }
//            }
//            prevItemMonth = item is TaskListCalendar.Month
//        }
//
//        if (data.isEmpty()) {
//            emptyItem {
//                id("emptyItem")
//                type(TypeEmptyItem.TASK)
//            }
//        }
//    }
//
//    private fun addSpace(id: String, space: Int) {
//        spaceItem {
//            id(id)
//            dimension(space)
//        }
//    }
//
//    interface OnClickListener {
//        fun onStep(id: String, step: TaskStep)
//        fun onTaskDetail(task: TaskPreview)
//    }
//}