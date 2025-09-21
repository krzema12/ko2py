

package topython

import model.python.alias
import model.python.aliasImpl

fun alias.toPython() =
    when (this) {
        is aliasImpl -> toPython()
    }

fun aliasImpl.toPython() =
    name.name
