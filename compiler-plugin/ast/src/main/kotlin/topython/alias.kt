

package topython

import model.python.alias.alias
import model.python.alias.aliasImpl


fun alias.toPython() =
    when (this) {
        is aliasImpl -> toPython()
    }

fun aliasImpl.toPython() =
    name.name
