/**
 * El Merkato الميركاتو -
 *
 * @author Mahmoud Aly
 * @version 1.0
 * @since 2020-12-04
 */
package com.ma7moud3ly.elmerkato.interfaces;

import com.google.firebase.auth.FirebaseUser;

public interface ActivityCallbacks {
    public void onLogin(FirebaseUser user);
}

