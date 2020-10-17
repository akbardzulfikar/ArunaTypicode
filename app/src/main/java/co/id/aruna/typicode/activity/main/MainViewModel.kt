package co.id.aruna.typicode.activity.main

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import co.id.aruna.typicode.data.db.AppDatabase
import co.id.aruna.typicode.data.db.UserDao
import co.id.aruna.typicode.data.model.UsersItem
import co.id.aruna.typicode.data.network.RfConfig
import com.google.gson.Gson
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.schedulers.Schedulers

class MainViewModel : ViewModel() {
    private val listUser : MutableLiveData<List<UsersItem>> = MutableLiveData()
    private var db: AppDatabase? = null
    private var userDao: UserDao? = null
    private var compositeDisposable: CompositeDisposable = CompositeDisposable()

    val getListUser : LiveData<List<UsersItem>> = listUser

    override fun onCleared() {
        compositeDisposable.dispose()
        super.onCleared()
    }

    fun initDatabase(context: Context) {
        db = AppDatabase.getAppDataBase(context)
        userDao = db?.userDao()
    }

    fun loadUser(context: Context){
        compositeDisposable.add(RfConfig.getRetrofit().getData()
            .subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe({ listUser ->
                insertData(listUser)
            }, { error ->
                getData()
                Log.e("MainViewModel", error.message!!)
                Toast.makeText(context, "Please check your internet connection", Toast.LENGTH_SHORT).show()
            })
        )
    }

    private fun insertData(listUsersItem: List<UsersItem>) {
        Observable.fromCallable {
            with(userDao) {
                this?.delete()
                this?.insertAll(listUsersItem)
            }
            db?.userDao()?.getAll()
        }.doOnNext { list ->
            listUser.postValue(list)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    private fun getData() {
        Observable.fromCallable {
            db?.userDao()?.getAll()
        }.doOnNext { list ->
            listUser.postValue(list)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }

    fun searchTitle(title: String) {
        Observable.fromCallable {
            db?.userDao()?.searchTitle(title)
        }.doOnNext { list ->
            Log.d("MainViewModel", Gson().toJson(list))
            listUser.postValue(list)
        }.subscribeOn(Schedulers.io())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe()
    }
}