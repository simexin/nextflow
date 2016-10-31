/*
 * Copyright (c) 2013-2016, Centre for Genomic Regulation (CRG).
 * Copyright (c) 2013-2016, Paolo Di Tommaso and the respective authors.
 *
 *   This file is part of 'Nextflow'.
 *
 *   Nextflow is free software: you can redistribute it and/or modify
 *   it under the terms of the GNU General Public License as published by
 *   the Free Software Foundation, either version 3 of the License, or
 *   (at your option) any later version.
 *
 *   Nextflow is distributed in the hope that it will be useful,
 *   but WITHOUT ANY WARRANTY; without even the implied warranty of
 *   MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 *   GNU General Public License for more details.
 *
 *   You should have received a copy of the GNU General Public License
 *   along with Nextflow.  If not, see <http://www.gnu.org/licenses/>.
 */

package nextflow.container

import spock.lang.Specification

/**
 *
 * @author Paolo Di Tommaso <paolo.ditommaso@gmail.com>
 */
class SingularityBuilderTest extends Specification {

    def 'should get the exec command line' () {

        expect:
        new SingularityBuilder('busybox')
                .build()
                .runCommand == 'env - PATH=$PATH singularity exec busybox'

        new SingularityBuilder('busybox')
                .params(entry: '/bin/bash')
                .build()
                .runCommand == 'env - PATH=$PATH singularity exec busybox /bin/bash'

        new SingularityBuilder('busybox')
                .params(engineOptions: '-q -v')
                .build()
                .runCommand == 'env - PATH=$PATH singularity -q -v exec busybox'

        new SingularityBuilder('busybox')
                .params(runOptions: '--contain --writable')
                .build()
                .runCommand == 'env - PATH=$PATH singularity exec --contain --writable busybox'

        new SingularityBuilder('busybox')
                .addEnv(ALPHA: 'aaa', BETA: 'bbb')
                .build()
                .runCommand == 'env - PATH=$PATH ALPHA=aaa BETA=bbb singularity exec busybox'

    }
}