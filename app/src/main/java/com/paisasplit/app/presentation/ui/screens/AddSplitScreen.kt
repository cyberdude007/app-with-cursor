package com.paisasplit.app.presentation.ui.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.paisasplit.app.presentation.viewmodel.AddSplitViewModel
import com.paisasplit.app.presentation.ui.components.AmountInputField

@Composable
fun AddSplitScreen(viewModel: AddSplitViewModel = hiltViewModel()) {
    val uiState by viewModel.uiState
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        AmountInputField(
            value = uiState.amount,
            onValueChange = viewModel::updateAmount,
            label = "Total Amount"
        )

        Spacer(modifier = Modifier.height(16.dp))

        Button(
            onClick = viewModel::saveSplitTransaction,
            modifier = Modifier.fillMaxWidth(),
            enabled = uiState.canSave
        ) {
            Text("Split & Save")
        }
    }
}


