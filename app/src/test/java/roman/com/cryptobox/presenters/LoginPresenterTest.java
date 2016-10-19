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

package roman.com.cryptobox.presenters;


import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import roman.com.cryptobox.activities.EditorActivity;
import roman.com.cryptobox.contracts.EditorContract;
import roman.com.cryptobox.contracts.LoginContract;
import roman.com.cryptobox.utils.MockNoteGenerator;
import roman.com.cryptobox.utils.PasswordHandler;

import static org.mockito.Mockito.verify;

public class LoginPresenterTest {

    @Mock
    private PasswordHandler mMockPasswordHandler;

    @Mock
    private LoginContract.View mMockLoginView;


    private LoginPresenter mLoginPresenter;

    @Before
    public void setupEditorPresenter() {
        // Mockito has a very convenient way to inject mocks by using the @Mock annotation. To
        // inject the mocks in the test the initMocks method needs to be called.
        MockitoAnnotations.initMocks(this);
        // Get a reference to the class under test
        mLoginPresenter = new LoginPresenter(mMockLoginView);
    }

    @Test
    public void isShowPasswordGoodCalled() {
        mLoginPresenter.loginButtonClicked("some_wrong_password");
        verify(mMockLoginView).showPasswordBad();
    }

}
