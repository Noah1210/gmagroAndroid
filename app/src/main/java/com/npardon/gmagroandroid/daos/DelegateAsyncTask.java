package com.npardon.gmagroandroid.daos;
public interface DelegateAsyncTask {
    /**
     * @param result Le resultat de la requête envoyé au serveur web
     */
    void whenWSIsTerminated(Object result) ;
}
