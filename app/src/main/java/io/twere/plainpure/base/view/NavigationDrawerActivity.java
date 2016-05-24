package io.twere.plainpure.base.view;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.LayoutRes;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import butterknife.Bind;
import butterknife.BindDimen;
import com.bumptech.glide.Glide;
import io.twere.plainpure.R;

public abstract class NavigationDrawerActivity extends BaseActivity {

  @Bind(R.id.drawerLayout) DrawerLayout drawerLayout;
  @Bind(R.id.vNavigation) NavigationView vNavigation;
  @BindDimen(R.dimen.global_menu_avatar_size) int avatarSize;

  private ImageView ivMenuUserProfilePhoto;

  @Override public void setContentView(@LayoutRes int layoutResID) {
    super.setContentViewWithoutInject(R.layout.activity_drawer);
    ViewGroup viewGroup = (ViewGroup) findViewById(R.id.flContentRoot);
    LayoutInflater.from(this).inflate(layoutResID, viewGroup, true);
    bindViews();
    setupHeader();
    setupItems();
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    getActivityComponent().inject(this);
  }

  @Override protected void setupToolbar() {
    super.setupToolbar();
    if (getToolbar() != null) {
      getToolbar().setNavigationOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));
    }
  }

  private void setupItems() {
    Menu menu = vNavigation.getMenu();

    menu.getItem(0).setChecked(true);
    if (getToolbar() != null) getToolbar().setTitle(menu.getItem(0).getTitle());

    menu.getItem(0).setOnMenuItemClickListener(menuItem -> {
      drawerLayout.closeDrawer(GravityCompat.START);
      menuItem.setChecked(true);
      onDrawerMenuClick(menuItem);
      if (getToolbar() != null) getToolbar().setTitle(menuItem.getTitle());
      return false;
    });

    menu.getItem(1).setOnMenuItemClickListener(menuItem -> {
      drawerLayout.closeDrawer(GravityCompat.START);
      menuItem.setChecked(true);
      onDrawerMenuClick(menuItem);
      if (getToolbar() != null) getToolbar().setTitle(menuItem.getTitle());
      return false;
    });

    menu.getItem(2).setOnMenuItemClickListener(menuItem -> {
      drawerLayout.closeDrawer(GravityCompat.START);
      menuItem.setChecked(true);
      onDrawerMenuClick(menuItem);
      if (getToolbar() != null) getToolbar().setTitle(menuItem.getTitle());
      return false;
    });
  }

  protected abstract void onDrawerMenuClick(MenuItem menuItem);

  protected abstract void onUserProfileClick(View view);

  private void setupHeader() {

    View headerView = vNavigation.getHeaderView(0);
    ivMenuUserProfilePhoto = (ImageView) headerView.findViewById(R.id.iv_avatar_profile);
    headerView.findViewById(R.id.ll_header).setOnClickListener(this::onGlobalMenuHeaderClick);

    Glide.with(this)
        .load("http://umdb.org.ua/images/imgs/__local10001/impsize/1243004483291.jpg")
        .placeholder(R.drawable.img_circle_placeholder)
        .override(avatarSize, avatarSize)
        .centerCrop()
        .into(ivMenuUserProfilePhoto);
  }

  private void onGlobalMenuHeaderClick(final View v) {
    drawerLayout.closeDrawer(GravityCompat.START);
    new Handler().postDelayed(() -> {
      int[] startingLocation = new int[2];
      v.getLocationOnScreen(startingLocation);
      startingLocation[0] += v.getWidth() / 2;
      // showProfile();
      TextView profileText = (TextView) v.findViewById(R.id.tv_name);
      if (getToolbar() != null) {
        if (profileText != null) getToolbar().setTitle(profileText.getText().toString());
      }
      onUserProfileClick(v);
      overridePendingTransition(0, 0);
    }, 200);
  }
}
