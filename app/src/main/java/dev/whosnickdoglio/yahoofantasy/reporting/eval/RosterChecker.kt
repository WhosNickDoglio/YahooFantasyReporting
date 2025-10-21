// Copyright (C) 2025 Nicholas Doglio
// SPDX-License-Identifier: MIT
package dev.whosnickdoglio.yahoofantasy.reporting.eval

import dev.whosnickdoglio.yahoofantasy.reporting.data.PlayerRowRawInfo

internal fun interface RosterChecker {
    fun check(roster: List<PlayerRowRawInfo>): Violation?
}

public enum class Violation {
    HEALTHY_ON_IL,
    ACTIVE_PLAYER_ON_BENCH_WITH_OPEN_STARTING_LINEUP_SPOT,
    IL_IN_STARTING_LINEUP,
    IL_PLAYER_ON_BENCH_WITH_OPEN_IL_SPOT,
}
