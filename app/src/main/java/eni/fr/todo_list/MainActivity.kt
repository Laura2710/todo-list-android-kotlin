package eni.fr.todo_list

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateMapOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import eni.fr.todo_list.ui.theme.TodolistTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TodolistTheme {
                TodoList()
            }
        }
    }
}

@Composable
fun TodoList(modifier: Modifier = Modifier) {
    var tasks = remember { mutableStateListOf<String>() }
    var task by remember { mutableStateOf("") }

    Column(
        modifier
            .fillMaxSize()
            .padding(32.dp)
    ) {
        Spacer(modifier = Modifier.height(32.dp))
        Column(
            modifier = modifier
                .fillMaxWidth()
                .padding(bottom = 20.dp)
        ) {
            Text(
                text = "Todo App",
                fontSize = 36.sp,
                textAlign = TextAlign.Center,
                modifier = modifier
                    .fillMaxWidth()
            )
        }

        FormToDo(task, onTaskChange = { task = it }, onAddTask = {
            if (task.isNotBlank()) {
                tasks.add(task)
                task = ""
            }
        }, modifier)
        Spacer(modifier = Modifier.height(32.dp))
        ListToDo(tasks = tasks)
    }
}


// Formulaire avec bouton
@Composable
fun FormToDo(
    task: String,
    onTaskChange: (String) -> Unit,
    onAddTask: () -> Unit,
    modifier: Modifier = Modifier
) {

    Column(modifier = modifier.fillMaxWidth()) {
        TextField(
            value = task,
            onValueChange = onTaskChange,
            label = { Text(text = "Todo") },
            modifier = Modifier.fillMaxWidth()
        )
        Spacer(modifier = Modifier.height(8.dp))
        Row(
            horizontalArrangement = Arrangement.End,
            modifier = Modifier.fillMaxWidth()
        ) {
            Button(
                onClick = onAddTask,
            ) {
                Text(text = "Ajouter")
            }
        }
    }
}

// Liste des tâches
@Composable
fun ListToDo(tasks: MutableList<String>, modifier: Modifier = Modifier) {
    val checkedStates = remember { mutableStateMapOf<String, Boolean>() }

    LazyColumn(
        modifier = modifier.fillMaxWidth()
    ) {
        items(tasks) { task ->
            val isChecked = checkedStates[task] ?: false

            Row(
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(8.dp)
            ) {
                Text(
                    text = task,
                    textDecoration = if (isChecked) TextDecoration.LineThrough else TextDecoration.None,
                    color = if (isChecked) Color.Gray else Color.Black

                )
                Checkbox(
                    checked = isChecked,
                    onCheckedChange = { checked -> checkedStates[task] = checked })
            }
        }
    }
}

@Preview(showBackground = true)
@Composable
fun TodoListPreview() {
    TodoList()
}