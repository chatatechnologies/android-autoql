package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.NotificationRepository
import chata.can.chata_ai.retrofit.data.model.NotificationModel

class GetNotificationUseCase {
	private val repository = NotificationRepository()

	suspend operator fun invoke(): List<NotificationModel> = repository.getNotification()
}