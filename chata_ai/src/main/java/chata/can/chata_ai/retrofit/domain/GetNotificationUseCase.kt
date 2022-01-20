package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.NotificationRepository

class GetNotificationUseCase {
	private val repository = NotificationRepository()

	suspend operator fun invoke() = repository.getNotification()
}