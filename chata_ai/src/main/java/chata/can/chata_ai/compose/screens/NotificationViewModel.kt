package chata.can.chata_ai.compose.screens

import androidx.lifecycle.ViewModel
import chata.can.chata_ai.compose.repository.NotificationRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class NotificationViewModel @Inject constructor(private val repository: NotificationRepository) :
	ViewModel() {
		//
}