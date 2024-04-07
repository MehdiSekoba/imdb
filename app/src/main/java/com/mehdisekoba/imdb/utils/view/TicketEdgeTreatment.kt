package com.mehdisekoba.imdb.utils.view
/*
https://medium.com/m/signin?actionUrl=https%3A%2F%2Fmedium.com%2F_%2Fbookmark%2Fp%2F5a7186e68fd&operation=register&redirect=https%3A%2F%2Flevelup.gitconnected.com%2Fandroid-tips-advanced-shapes-for-your-drawables-5a7186e68fd&source=-----5a7186e68fd---------------------bookmark_footer-----------
 */
import com.google.android.material.shape.EdgeTreatment
import com.google.android.material.shape.ShapePath

class TicketEdgeTreatment(
    private val size: Float,
) : EdgeTreatment() {
    override fun getEdgePath(
        length: Float,
        center: Float,
        interpolation: Float,
        shapePath: ShapePath,
    ) {
        val circleRadius = size * interpolation
        shapePath.lineTo(center - circleRadius, 0f)
        shapePath.addArc(
            center - circleRadius,
            -circleRadius,
            center + circleRadius,
            circleRadius,
            180f,
            -180f,
        )
        shapePath.lineTo(length, 0f)
    }
}
