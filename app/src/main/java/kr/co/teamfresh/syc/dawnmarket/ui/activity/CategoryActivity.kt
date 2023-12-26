package kr.co.teamfresh.syc.dawnmarket.ui.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import kr.co.teamfresh.syc.dawnmarket.R
import kr.co.teamfresh.syc.dawnmarket.databinding.ActivityCategoryBinding
import kr.co.teamfresh.syc.dawnmarket.databinding.ActivityMainBinding

class CategoryActivity : AppCompatActivity() {
    private lateinit var binding: ActivityCategoryBinding
    private lateinit var viewModel: CategoryViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_category)

        viewmodel = ViewModelProvider(this).get(CategoryViewModel::class.java)
        lifecyc
    }

}