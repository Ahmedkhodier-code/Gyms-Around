package com.example.gymsaround

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Place
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.gymsaround.ui.theme.GymsAroundTheme
import com.example.gymsaround.ui.theme.Purple40

@Composable
fun GymsScreen() {
    val vm: GymsViewModel = viewModel()

    LazyColumn {
        items(vm.state) { gym ->
            GymItem(gym) {
                vm.toggleFaveState(gym.gymId)
                Log.i("TAG1", "GymItem: $it")
            }
        }
    }
}

@Composable
fun GymItem(
    gym: Gym, onClick: (Gym) -> Unit
) {
    val icon = if (gym.isFav) {
        Icons.Filled.Favorite
    } else {
        Icons.Filled.FavoriteBorder
    }
    Card(
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
        modifier = Modifier.padding(8.dp)
    ) {
        Row(verticalAlignment = Alignment.CenterVertically, modifier = Modifier.padding(8.dp)) {
            DefaultIcon(Icons.Filled.Place, Modifier.weight(0.15f), "Gym Icon")
            GymDetails(gym, Modifier.weight(0.50f))
            DefaultIcon(icon, Modifier.weight(0.15f), "fav Gym Icon") {
                onClick(gym)
                Log.i("TAG1.1", "GymItem: $gym")
            }
        }
    }
}

@Composable
fun DefaultIcon(
    icon: ImageVector,
    modifier: Modifier,
    contentDescription: String,
    onClick: () -> Unit = {}
) {
    Image(
        imageVector = icon,
        contentDescription = contentDescription,
        modifier = modifier
            .padding(8.dp)
            .clickable {
                onClick()
            },
        colorFilter = ColorFilter.tint(
            Color.DarkGray
        )
    )
}

@Composable
fun GymDetails(gym: Gym, modifier: Modifier) {
    Column(modifier = modifier) {
        Text(text = gym.name, style = MaterialTheme.typography.headlineLarge, color = Purple40)
        Text(
            text = gym.place,
            style = MaterialTheme.typography.bodyMedium,
            modifier = Modifier.alpha(0.5f),
        )
    }
}

@Preview(showBackground = true)
@Composable
fun _GymScreenPreview() {
    GymsAroundTheme {
        GymsScreen()
    }
}
