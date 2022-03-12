package chata.can.chata_ai.retrofit.domain

import chata.can.chata_ai.retrofit.data.NotificationRepository
import chata.can.chata_ai.retrofit.data.model.notification.NotificationDataModel

class GetNotificationUseCase {
	private val repository = NotificationRepository()

	suspend operator fun invoke(): NotificationDataModel = repository.getNotification()
}