package kg.musabaev.onlinetutorback.repository.projection;

import java.time.Instant;

public interface GroupClassItemView extends BaseClassItemView {
	Instant getStartDateTime();
	Instant getFinishDateTime();
}