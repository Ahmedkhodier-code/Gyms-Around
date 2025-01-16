package com.example.gymsaround

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel

@Composable
fun GymsDetailsScreen() {
    val vm: GymsDetailsViewModel = viewModel()
    val item = vm.state.value
    item?.let {

    }
}