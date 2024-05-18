package com.deletech.malakoff.components

import androidx.annotation.StringRes
import com.deletech.malakoff.R


/**
 * Error state holding values for error ui
 */
data class ErrorState(
    val hasError: Boolean = false,
    @StringRes val errorMessageStringResource: Int = R.string.empty_string
)