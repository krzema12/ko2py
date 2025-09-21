

package topython

import generated.Python.alias
import generated.Python.aliasImpl

fun alias.toPython() =
    when (this) {
        is aliasImpl -> toPython()
    }

fun aliasImpl.toPython() =
    name.name
