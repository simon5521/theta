package hu.bme.mit.theta.markov

data class Distribution(val values: Map<Location, Double>)

data class Automaton(
        val actions: Set<Action>,
        val inputEnable: Set<Action>,
        val locations: Set<Location>,
        val initialDistribution: Distribution
)

data class Location(
        val timedTransitions: Distribution,
        val labeledTransitions: Set<LabeledTransition>
)

data class LabeledTransition(
        val label: Action,
        val priority: Int,
        val targets: Distribution
)

interface Action
