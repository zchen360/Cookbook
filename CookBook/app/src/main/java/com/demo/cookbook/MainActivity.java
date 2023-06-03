package com.demo.cookbook;

import android.os.Build;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toolbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.demo.cookbook.ui.favor.FavorFragment;
import com.demo.cookbook.ui.home.HomeFragment;
import com.demo.cookbook.ui.shop.ShopFragment;

public class MainActivity extends AppCompatActivity {
    private static final int HOME_TAB_INDEX = 0;
    private static final int FAVOR_TAB_INDEX = 1;
    private static final int SHOP_TAB_INDEX = 2;
    private int mCurTabIndex = HOME_TAB_INDEX;
    private RadioButton rbHome, rbFavor, rbShop;
    private TextView toolbar;
    private Fragment mCurFragment = null;

    private Fragment homeFragment, favorFragment, shopFragment;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setStatusIconColorAndActionBar();

        setContentView(R.layout.activity_main);

        toolbar = findViewById(R.id.toolBar);
        rbHome = findViewById(R.id.rb_home);
        rbFavor = findViewById(R.id.rb_favor);
        rbShop = findViewById(R.id.rb_shop);

        rbHome.setOnClickListener(view -> {
            mCurTabIndex = HOME_TAB_INDEX;
            toolbar.setText(getString(R.string.title_home));
            changeFragment();
        });
        rbFavor.setOnClickListener(view -> {
            mCurTabIndex = FAVOR_TAB_INDEX;
            toolbar.setText(getString(R.string.title_favor));
            changeFragment();
        });
        rbShop.setOnClickListener(view -> {
            mCurTabIndex = SHOP_TAB_INDEX;
            toolbar.setText(getString(R.string.title_shop));
            changeFragment();
        });
        changeFragment();
    }

    public void setStatusIconColorAndActionBar() {
        getSupportActionBar().hide();
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            View decorView = getWindow().getDecorView();
            getWindow().setStatusBarColor(getColor(R.color.white));
            if (decorView != null) {
                int vis = decorView.getSystemUiVisibility();
                vis |= View.SYSTEM_UI_FLAG_LIGHT_STATUS_BAR;
                decorView.setSystemUiVisibility(vis);
            }
        }
    }

    private Fragment getFragmentByTabIndex(int index) {
        if (index == HOME_TAB_INDEX) {
            if (homeFragment == null) {
                homeFragment = new HomeFragment();
            }
            return homeFragment;
        } else if (index == FAVOR_TAB_INDEX) {
            if (favorFragment == null) {
                favorFragment = new FavorFragment();
            }
            return favorFragment;
        } else if (index == SHOP_TAB_INDEX) {
            if (shopFragment == null) {
                shopFragment = new ShopFragment();
            }
            return shopFragment;
        }
        return null;
    }

    private void changeFragment() {
        Fragment targetFragment = getFragmentByTabIndex(mCurTabIndex);
        if (targetFragment == null) {
            return;
        }
        if (targetFragment == mCurFragment) {
            return;
        }

        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        if (targetFragment.isAdded()) {
            transaction.hide(this.mCurFragment).show(targetFragment);
            try {
                transaction.commitAllowingStateLoss();
            } catch (Exception e) {

            }
        } else {
            if (this.mCurFragment != null) {
                transaction.hide(this.mCurFragment);
            }
            try {
                transaction.add(R.id.fl_container, targetFragment, targetFragment.getClass().getName());
            } catch (Exception ignored) {
                transaction.show(targetFragment);
            } finally {
                try {
                    transaction.commitAllowingStateLoss();
                } catch (Exception e) {

                }
            }
        }
        this.mCurFragment = targetFragment;
    }
}