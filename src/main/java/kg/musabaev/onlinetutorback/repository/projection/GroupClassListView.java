package kg.musabaev.onlinetutorback.repository.projection;

import java.time.Instant;

public interface GroupClassListView extends BaseClassListView {
	Instant getStartDateTime();
	Instant getFinishDateTime();
}