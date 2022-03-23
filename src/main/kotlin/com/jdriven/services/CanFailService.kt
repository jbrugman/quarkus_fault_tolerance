package com.jdriven.services

import javax.enterprise.context.ApplicationScoped

@ApplicationScoped
class CanFailService {

    // e.g. connect to sftp and write something.
    fun unstable(i : Int) : Int = i

}