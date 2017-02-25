/*
 * Copyright 2015, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cryptobox.presenters;


import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import cryptobox.contracts.LoginContract;

import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class LoginPresenterTest implements BaseTest{

    @Mock
    private LoginContract.View mMockLoginView;

    @Mock
    private LoginContract.Model mMockLoginModel;

    String password = "1234";


    private LoginPresenter mLoginPresenter;


    @Override
    @Before
    public void setup() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);
        // Get a reference to the class under test
        mLoginPresenter = new LoginPresenter(mMockLoginModel);
        mLoginPresenter.setView(mMockLoginView);
    }


    @Test
    public void isShowPasswordBadCalled() {
        when(mMockLoginModel.verifyPassword(password)).thenReturn(false);
        mLoginPresenter.loginButtonClicked(password);
        verify(mMockLoginView, times(1)).showPasswordBad();
    }


}
