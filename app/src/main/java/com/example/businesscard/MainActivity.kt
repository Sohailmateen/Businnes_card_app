package com.example.businesscard

import android.content.Intent
import android.content.res.Configuration
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Link
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.businesscard.ui.theme.BusinessCardTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BusinessCardTheme {
                Scaffold(
                    modifier = Modifier.fillMaxSize(),
                    containerColor = MaterialTheme.colorScheme.background
                ) { innerPadding ->
                    Box(modifier = Modifier.padding(innerPadding)) {
                        DeveloperBusinessCard()
                    }
                }
            }
        }
    }
}

/* ----------------------- DATA ----------------------- */

data class Developer(
    val name: String,
    val title: String,
    val bio: String,
    val email: String,
    val linkedin: String,
    val github: String
)

val developer = Developer(
    name = "Sohail Mateen",
    title = "CSE - Data Science Student",
    bio = "Passionate about Machine Learning and AI solutions.",
    email = "sohailmateen001@email.com",
    linkedin = "https://linkedin.com/in/sohail-mateen-196405284",
    github = "https://github.com/Sohailmateen"
)

/* ----------------------- MAIN CARD ----------------------- */

@Composable
fun DeveloperBusinessCard() {
    val isLandscape =
        LocalConfiguration.current.orientation == Configuration.ORIENTATION_LANDSCAPE

    if (isLandscape) {
        LandscapeLayout(developer)
    } else {
        PortraitLayout(developer)
    }
}

/* ----------------------- PORTRAIT ----------------------- */

@Composable
fun PortraitLayout(dev: Developer) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(16.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Column(
                modifier = Modifier.padding(24.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                ProfileImage()

                Spacer(modifier = Modifier.height(16.dp))

                NameSection(dev)

                Spacer(modifier = Modifier.height(20.dp))

                ContactSection(dev)
            }
        }
    }
}

/* ----------------------- LANDSCAPE ----------------------- */

@Composable
fun LandscapeLayout(dev: Developer) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        contentAlignment = Alignment.Center
    ) {
        Card(
            modifier = Modifier
                .padding(24.dp)
                .fillMaxWidth(),
            elevation = CardDefaults.cardElevation(8.dp),
            colors = CardDefaults.cardColors(
                containerColor = MaterialTheme.colorScheme.surface
            )
        ) {
            Row(
                modifier = Modifier.padding(24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceEvenly
            ) {
                ProfileImage(size = 160.dp)

                Spacer(modifier = Modifier.width(24.dp))

                Column {
                    NameSection(dev)
                    Spacer(modifier = Modifier.height(16.dp))
                    ContactSection(dev)
                }
            }
        }
    }
}

/* ----------------------- COMPONENTS ----------------------- */

@Composable
fun ProfileImage(size: androidx.compose.ui.unit.Dp = 120.dp) {
    Image(
        painter = painterResource(id = R.drawable.profile),
        contentDescription = "Profile Image",
        modifier = Modifier
            .size(size)
            .clip(CircleShape)
    )
}

@Composable
fun NameSection(dev: Developer) {
    Text(
        text = dev.name,
        fontSize = 28.sp,
        fontWeight = FontWeight.Bold,
        color = MaterialTheme.colorScheme.onSurface
    )

    Text(
        text = dev.title,
        fontSize = 18.sp,
        color = MaterialTheme.colorScheme.primary
    )

    Spacer(modifier = Modifier.height(8.dp))

    Text(
        text = dev.bio,
        fontSize = 14.sp,
        textAlign = TextAlign.Center,
        color = MaterialTheme.colorScheme.onSurfaceVariant
    )
}

@Composable
fun ContactSection(dev: Developer) {
    ContactRow(Icons.Default.Email, dev.email, "email")
    ContactRow(Icons.Default.Link, dev.linkedin, "link")
    ContactRow(Icons.Default.Code, dev.github, "link")
}

@Composable
fun ContactRow(icon: ImageVector, text: String, type: String) {
    val context = LocalContext.current

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 6.dp)
            .clickable {
                when (type) {
                    "email" -> {
                        val intent = Intent(Intent.ACTION_SENDTO).apply {
                            data = Uri.parse("mailto:$text")
                        }
                        context.startActivity(intent)
                    }

                    "link" -> {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(text))
                        context.startActivity(intent)
                    }
                }
            },
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.width(12.dp))

        Text(
            text = text,
            fontSize = 14.sp,
            color = MaterialTheme.colorScheme.onSurface
        )
    }
}

/* ----------------------- PREVIEW ----------------------- */

@Preview(showBackground = true)
@Composable
fun CardPreview() {
    BusinessCardTheme {
        DeveloperBusinessCard()
    }
}