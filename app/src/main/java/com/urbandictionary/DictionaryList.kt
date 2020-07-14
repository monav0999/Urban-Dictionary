package com.urbandictionary

import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuItem
import android.view.View
import android.widget.PopupMenu
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.SearchView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.urbandictionary.adapter.DictionaryAdapter
import com.urbandictionary.remote.Dictionary
import com.urbandictionary.remote.Information
import com.urbandictionary.util.ApiClient
import com.urbandictionary.util.ApiInterface
import com.urbandictionary.util.Common
import kotlinx.android.synthetic.main.dictionary_list.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class DictionaryList : AppCompatActivity(), PopupMenu.OnMenuItemClickListener {

    private lateinit var mInformationList: MutableList<Information>

    private var menuSort: MenuItem? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dictionary_list)
    }

    /**
     * API call to retrieve list and populate with RecyclerView on success
     */
    private fun getList(term: String) {
        showProgressBar()

        val apiInterface = ApiClient.getClient()?.create(ApiInterface::class.java)

        val call = apiInterface?.getList(term)
        call?.enqueue(object : Callback<Dictionary> {
            override fun onResponse(call: Call<Dictionary>, response: Response<Dictionary>) {
                hideProgressBar()
                val list = response.body()
                if (list?.dictionary != null && list.dictionary!!.isNotEmpty()) {
                    mInformationList = list.dictionary as MutableList<Information>
                    setupData()
                } else {
                    Common.showErrorDialog(this@DictionaryList, getString(R.string.NO_DATA))
                    menuSort?.isVisible = false
                    rvDictionaryList.visibility = View.GONE
                    llWelcomeScreen.visibility = View.VISIBLE
                }
            }

            override fun onFailure(call: Call<Dictionary>, t: Throwable) {
                hideProgressBar()
                Log.i("TAG", "Error: $t")
                Common.showErrorDialog(this@DictionaryList, getString(R.string.SERVER_ERROR))
            }
        })
    }

    /**
     * Setup RecyclerView with data retrieved from API
     */
    private fun setupData() {
        menuSort?.isVisible = true
        llWelcomeScreen.visibility = View.GONE
        val manager = LinearLayoutManager(this)
        manager.orientation = RecyclerView.VERTICAL
        rvDictionaryList.layoutManager = manager
        rvDictionaryList.adapter = DictionaryAdapter(this, mInformationList)
        rvDictionaryList.visibility = View.VISIBLE
    }

    /**
     * Show popup menu items for list main_menu options
     *
     * @param view toolbar item view
     */
    private fun showPopupMenu(view: View) {
        val popup = PopupMenu(this, view)
        popup.setOnMenuItemClickListener(this)
        popup.inflate(R.menu.popup_menu)
        popup.show()
    }

    /**
     * Show ProgressBar
     */
    private fun showProgressBar() {
        progressBar.visibility = View.VISIBLE
    }

    /**
     * Hide ProgressBar
     */
    private fun hideProgressBar() {
        progressBar.visibility = View.GONE
    }

    /**
     * Toolbar item click event
     *
     * @param item selected menu item
     * @return
     */
    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.itemId) {
            R.id.menuSort -> showPopupMenu(findViewById(item.itemId))
        }
        return true
    }

    /**
     * Setup menu items on Toolbar
     *
     * @param menu
     * @return
     */
    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.main_menu, menu)

        menuSort = menu?.findItem(R.id.menuSort)

        val searchItem: MenuItem? = menu?.findItem(R.id.menuSearch)
        val searchView: SearchView? = searchItem?.actionView as SearchView
        searchView?.queryHint = "Type any word..."

        searchView?.setOnQueryTextListener(object :
            SearchView.OnQueryTextListener {
            override fun onQueryTextSubmit(query: String): Boolean {
                if (Common.isInternetConnected(this@DictionaryList)) {
                    getList(query)
                } else {
                    Common.showErrorDialog(
                        this@DictionaryList,
                        getString(R.string.CONNECTION_ERROR)
                    )
                }

                searchItem.collapseActionView()
                return true
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                return true
            }
        })

        return true
    }

    /**
     * Popup Menu item click event
     *
     * @param menuItem selected popup menu item
     * @return
     */
    override fun onMenuItemClick(menuItem: MenuItem): Boolean {
        val sortedData = when (menuItem.itemId) {
            R.id.menuSortThumbsUp -> mInformationList.sortedByDescending { it.thumbsUp }
            R.id.menuSortThumbsDown -> mInformationList.sortedByDescending { it.thumbsDown }
            else -> mInformationList
        }

        mInformationList.clear()
        mInformationList.addAll(sortedData)
        rvDictionaryList.adapter?.notifyDataSetChanged()
        return true
    }
}
