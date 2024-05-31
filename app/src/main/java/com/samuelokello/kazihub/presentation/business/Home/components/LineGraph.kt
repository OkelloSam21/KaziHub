package com.samuelokello.kazihub.presentation.business.Home.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.samuelokello.kazihub.presentation.worker.data.Job


/**
 *  composable function that draws a line graph
 *  @param jobs list of jobs fetched from kazi hub api
 */
@Composable
fun LineGraph(jobs: List<Job>) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Text(
            text = "JOB BUDGET OVER TIME",
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        // Define graph dimensions
        val graphHeight = 200.dp
        val graphWidth = 300.dp
        val padding = 8.dp

        // Extract non-null budgets, or use 0 if the budget is null
        val budgets = jobs.map { it.budget ?: 0 }
        val maxValue = budgets.maxOrNull() ?: 1
        val minValue = budgets.minOrNull() ?: 0

        Canvas(
            modifier = Modifier
                .size(graphWidth, graphHeight)
                .padding(padding)
        ) {
            val paddingPx = padding.toPx()
            val widthPerPoint =
                if (jobs.size > 1) (size.width - paddingPx * 2) / (jobs.size - 1) else 0f
            val heightPerPoint =
                if (maxValue != minValue) (size.height - paddingPx * 2) / (maxValue - minValue) else 1f

            // Draw axes
            drawLine(
                Color.Gray,
                Offset(paddingPx, paddingPx),
                Offset(paddingPx, size.height - paddingPx)
            )
            drawLine(
                Color.Gray,
                Offset(paddingPx, size.height - paddingPx),
                Offset(size.width - paddingPx, size.height - paddingPx)
            )

            if (jobs.isNotEmpty()) {
                for (i in 0 until jobs.size - 1) {
                    val startBudget = jobs[i].budget ?: 0
                    val endBudget = jobs[i + 1].budget ?: 0

                    val start = Offset(
                        paddingPx + i * widthPerPoint,
                        size.height - paddingPx - (startBudget - minValue) * heightPerPoint
                    )
                    val end = Offset(
                        paddingPx + (i + 1) * widthPerPoint,
                        size.height - paddingPx - (endBudget - minValue) * heightPerPoint
                    )
                    drawLine(Color.Blue, start, end, strokeWidth = 4f)
                }

                // Draw circles at each data point
                jobs.forEachIndexed { index, job ->
                    val budget = job.budget ?: 0
                    val point = Offset(
                        paddingPx + index * widthPerPoint,
                        size.height - paddingPx - (budget - minValue) * heightPerPoint
                    )
                    drawCircle(Color.Red, radius = 6f, center = point)
                }

                // if jobs is empty add text place holder inside the graph

            }


        }

    }

}