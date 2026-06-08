package com.ems.util;

import com.ems.model.Employee;

public class Session {
    public static Employee currentUser = null;
    
    public static void logout() {
        currentUser = null;
    }
}
