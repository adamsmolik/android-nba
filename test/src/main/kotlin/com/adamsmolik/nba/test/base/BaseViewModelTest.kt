package com.adamsmolik.nba.test.base

import com.adamsmolik.nba.core.base.arch.BaseViewModel
import com.adamsmolik.nba.test.MainDispatcherRule
import org.junit.Before
import org.junit.Rule

@SuppressWarnings("VariableNaming")
open class BaseViewModelTest<T : BaseViewModel> {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    open fun mock() {
    }
}
