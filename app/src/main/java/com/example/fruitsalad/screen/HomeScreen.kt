package com.example.fruitsalad.screen

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material.icons.filled.Menu
import androidx.compose.material.icons.filled.Search
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.fruitsalad.R
import com.example.fruitsalad.animation.CardData

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onCardClick: (CardData) -> Unit,
    onComboClick: (CardData) -> Unit
) {
    var selectedCategory by remember { mutableStateOf("Hottest") }

    Box(modifier = modifier.fillMaxSize()) {

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(color = Color.White)
                .statusBarsPadding()
                .padding(horizontal = 16.dp)
        ) {

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 16.dp),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {

                Icon(
                    imageVector = Icons.Default.Menu,
                    contentDescription = null,
                    tint = Color(0xFF272140),
                    modifier = Modifier.size(24.dp)
                )

                Column(horizontalAlignment = Alignment.CenterHorizontally) {

                    Image(
                        painter = painterResource(R.drawable.shopping_basket),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp)
                    )

                    Spacer(modifier = Modifier.height(4.dp))

                    Text("My Basket", fontSize = 12.sp, color = Color(0xFF272140))
                }
            }

            //        Greeting Text
            Text(text = "Hello Tony, What fruit salad combo do you want today? ",
                fontSize = 20.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF272140),
                lineHeight = 20.sp,
                modifier= Modifier.padding(bottom = 24.dp)
            )

            //        Search bar

            Row(modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0xFFF3F1F1), shape = RoundedCornerShape(16.dp)
                )
                .padding(16.dp), verticalAlignment = Alignment.CenterVertically

            ) {

                Icon(imageVector = Icons.Default.Search,
                    contentDescription = null, tint = Color(0xFF86869E),
                    modifier = Modifier.size(24.dp)
                )

                Spacer(modifier = Modifier.width(12.dp))

                Text("Search for fruit salad combo", fontSize = 14.sp, color = Color(0xFF86869E))

                Spacer(modifier=Modifier.weight(1f))

                Image(
                    painter = painterResource(R.drawable.filter_list),
                    contentDescription = null,
                    modifier=Modifier.size(20.dp)
                )
            }

            Spacer(modifier = Modifier.height(32.dp))

            Text("Recommended Combo", fontSize = 24.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.padding(bottom = 16.dp)

            )

            LazyRow(horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.padding(bottom = 16.dp)
            ) {

                items (count =   2){ index ->

                    val comboName = if (index == 0) "Honey limb combo" else "Berry mangoc orbo "
                    val comboPrice = if (index == 0) "$1000" else "$800"
                    val comboImg = if (index == 0) R.drawable.food_1 else R.drawable.food_2
                    val comboColor = if (index == 0) Color(0xFFFFFAEB) else Color(0xFFF1EFF6)

                    RecmmendedComboCard(
                        name = comboName,
                        price = comboPrice,
                        imageRes = comboImg,
                        onClick = {
                            onComboClick(CardData(comboName, comboPrice, comboImg, comboColor))
                        }
                    )
                }
            }

            //        category tab

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(24.dp),
                modifier = Modifier.padding(bottom = 24.dp)
            ) {
                items(listOf("Hottest", "Popular", "New Combo", "Top")) { category ->
                    val isSelected = category == selectedCategory
                    Column(
                        horizontalAlignment = Alignment.Start,
                        modifier = Modifier.clickable(
                            interactionSource = remember { MutableInteractionSource() },
                            indication = null
                        ) {
                            selectedCategory = category
                        }
                    ) {
                        Text(
                            text = category,
                            fontSize = if (isSelected) 24.sp else 18.sp,
                            fontWeight = if (isSelected) FontWeight.Bold else FontWeight.Medium,
                            color = if (isSelected) Color(0xFF272140) else Color(0xFF938DB5)
                        )
                        if (isSelected) {
                            Box(
                                modifier = Modifier
                                    .padding(top = 4.dp)
                                    .width(24.dp)
                                    .height(2.dp)
                                    .background(Color(0xFFFF8C42), shape = RoundedCornerShape(2.dp))
                            )
                        }
                    }
                }
            }

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(16.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                items(3) { index ->
                    val foodName = when (index) {
                        0 -> "Quinoa fruit salad"
                        1 -> "Tropical fruit salad"
                        else -> "Melon fruit salad"
                    }
                    val foodPrice = "$10,000"
                    val foodImg = when (index) {
                        0 -> R.drawable.food_4
                        1 -> R.drawable.fruit_basket_image
                        else -> R.drawable.food_1
                    }
                    val foodColor = when (index) {
                        0 -> Color(0xFFFFFAEB)
                        1 -> Color(0xFFFEF0F0)
                        else -> Color(0xFFF1EFF6)
                    }
                    CategoryFoodCard(
                        name = foodName,
                        price = foodPrice,
                        imageRes = foodImg,
                        containerColor = foodColor,
                        modifier = Modifier.clickable {
                            onCardClick(CardData(foodName, foodPrice, foodImg, foodColor))
                        }
                    )
                }
            }

        }
    }
}

@Composable
fun CategoryFoodCard(
    modifier: Modifier = Modifier,
    name: String,
    price: String,
    imageRes: Int,
    containerColor: Color
) {
    Card(
        modifier = modifier
            .width(150.dp)
            .height(210.dp), // Set fixed height
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = containerColor),
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween
        ) {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = Color(0xFFFF8C42),
                    modifier = Modifier.size(20.dp)
                )
            }

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )

            Text(
                text = name,
                fontSize = 14.sp,
                fontWeight = FontWeight.Medium,
                color = Color(0xFF272140),
                textAlign = TextAlign.Center
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = price,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Normal,
                    color = Color(0xFFF08626)
                )

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color(0xFFFF8C42),
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            Color(0xFFFFF2E7), shape = CircleShape
                        )
                )
            }
        }
    }
}

@Composable
fun RecmmendedComboCard(
    modifier: Modifier = Modifier,
    name: String,
    price: String,
    imageRes: Int,
    onClick: () -> Unit = {}
) {

    Card(
        modifier = modifier
            .width(170.dp)
            .clickable(
                interactionSource = remember { MutableInteractionSource() },
                indication = null,
                onClick = onClick
            ),
        shape = RoundedCornerShape(16.dp),
        colors = CardDefaults.cardColors(containerColor = Color.White),
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.End
            ) {
                Icon(
                    imageVector = Icons.Default.FavoriteBorder,
                    contentDescription = null,
                    tint = Color(0xFFFF8C42),
                    modifier = Modifier.size(20.dp)
                )
            }

            Image(
                painter = painterResource(id = imageRes),
                contentDescription = null,
                modifier = Modifier.size(80.dp)
            )

            Spacer(modifier = Modifier.height(12.dp))

            Text(
                text = name,
                fontSize = 16.sp,
                fontWeight = FontWeight.Medium,
                color = Color.Black,
                modifier = Modifier.align(Alignment.Start)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = price,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    color = Color(0xFFFF8C42)
                )

                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = null,
                    tint = Color(0xFFFF8C42),
                    modifier = Modifier
                        .size(24.dp)
                        .background(
                            Color(0xFFFFF2E7), shape = CircleShape
                        )
                )
            }
        }
    }
}


@Composable
@Preview(showBackground = true)
fun HomeScreenPrev(modifier: Modifier = Modifier) {
    HomeScreen(onCardClick = {}, onComboClick = {})
}
