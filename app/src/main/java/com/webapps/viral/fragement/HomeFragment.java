package com.webapps.viral.fragement;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.widget.NestedScrollView;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.LinearSmoothScroller;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.webapps.viral.R;
import com.webapps.viral.activity.AddStoryActivity;
import com.webapps.viral.activity.StatusActivity;
import com.webapps.viral.adapter.CategoryAdapter;
import com.webapps.viral.adapter.PostAdapter;
import com.webapps.viral.model.ChildModel;
import com.webapps.viral.model.PostModel;
import com.webapps.viral.model.UserStory;
import com.webapps.viral.utils.APIService;
import com.webapps.viral.utils.RetrofitClient;
import com.webapps.viral.utils.SharedPref;
import com.webapps.viral.utils.SharedPreferenceUtil;
import com.webapps.viral.utils.ShowHideViewListener;

import java.util.ArrayList;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;


public class HomeFragment extends Fragment {

    PostAdapter foodAdapter;
    static FloatingActionButton floating_home;
    CategoryAdapter categoryAdapter;
    RecyclerView rv_category,rv_food,rv_missed,rv_donation;
    ArrayList<ChildModel> foodlist,mislist;
    ArrayList<UserStory> category;
    ArrayList<PostModel> postModels;
    ArrayList<UserStory> userStoryArrayList;
    LinearLayout root;
    ArrayList<ChildModel>   donation;
    Retrofit retrofit;
    APIService service;
    FloatingActionButton floatingActionButton;
    Integer[] image ={R.drawable.ic_add_circle_black,R.drawable.c1, R.drawable.c2,R.drawable.c3,R.drawable.c4,R.drawable.c5};
   // Integer[] categor ={R.drawable.ic_t_shirt,R.drawable.ic_food_,R.drawable.ic_breakfast,R.drawable.ic_fish};
    TextView txt_allfood;
    EditText search;
    SharedPref sharedPref;
    String uid;
    ShimmerFrameLayout shimmerFrameLayout;
    SwipeRefreshLayout swipeRefreshLayout;
    NestedScrollView nestedScrollView;
    private final int REQUEST_CODE_PERMISSION = 55;
    private ShowHideViewListener showHideViewListener;

    private static float MAX_SWIPE_DISTANCE_FACTOR = 0.6f;
    private static int DEFAULT_REFRESH_TRIGGER_DISTANCE = 200;
    private static final int HIDE_THRESHOLD = 25;
    private int scrolledDistance = 0;
    private boolean controlsVisible = true, wait;
    private View emptyView;
    private TextView empty_view_text;
    private LinearLayoutManager linearLayoutManager;
    private String postType;
    private SharedPreferenceUtil sharedPreferenceUtil;
    private int pageNumber = 1, userId;
    private boolean allDone, isLoading, storyProgress, showStory;
    private int refreshTriggerDistance = DEFAULT_REFRESH_TRIGGER_DISTANCE;
    private NestedScrollView.OnScrollChangeListener nestedScrollViewChangeListener = new NestedScrollView.OnScrollChangeListener() {
        @Override
        public void onScrollChange(NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
            if (scrollY > oldScrollY) {
            }
            if (scrollY < oldScrollY) {
            }
            if (showHideViewListener != null && !wait) {
                if (scrolledDistance > HIDE_THRESHOLD && controlsVisible) {
                    showHideViewListener.hideView();
                    wait = true;
                    controlsVisible = false;
                    scrolledDistance = 0;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wait = false;
                        }
                    }, 600);
                } else if (scrolledDistance < -HIDE_THRESHOLD && !controlsVisible) {
                    showHideViewListener.showView();
                    wait = true;
                    controlsVisible = true;
                    scrolledDistance = 0;
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            wait = false;
                        }
                    }, 600);
                }
                int dy = scrollY - oldScrollY;
                if ((controlsVisible && dy > 0) || (!controlsVisible && dy < 0)) {
                    scrolledDistance += dy;
                }
            }
            if (v.getChildAt(v.getChildCount() - 1) != null) {
                if ((scrollY >= (v.getChildAt(v.getChildCount() - 1).getMeasuredHeight() - v.getMeasuredHeight())) && scrollY > oldScrollY) {
                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();
                    if ((visibleItemCount + pastVisiblesItems) >= totalItemCount && !isLoading && !allDone) {
                        Log.e("CHECK_LAST", "loading more");
                        pageNumber++;
                        //homeRecyclerAdapter.showLoading();
                        GetPost();
                    }
                }
            }
        }
    };
    public void refresh() {
        pageNumber = 1;
        foodAdapter.clear();
        allDone = false;
        if (showStory) GetStory();
        GetPost();
        emptyView.setVisibility(View.GONE);
        rv_food.setVisibility(View.VISIBLE);
    }
    private void initialize() {
        GetPost();
        emptyView.setVisibility(View.GONE);
        rv_food.setVisibility(View.VISIBLE);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = LayoutInflater.from(getActivity()).inflate(R.layout.home_fragment, container, false);

        //DashBoard.toolbar.setTitle("Home");
        swipeRefreshLayout = view.findViewById(R.id.frag_home_feeds_swipe_refresh_layout);
        nestedScrollView = view.findViewById(R.id.scrollView);
        nestedScrollView.setOnScrollChangeListener(nestedScrollViewChangeListener);
        shimmerFrameLayout=view.findViewById(R.id.parentShimmerLayout);
        shimmerFrameLayout.startShimmerAnimation();
        DisplayMetrics metrics = getResources().getDisplayMetrics();
        Float mDistanceToTriggerSync = Math.min(swipeRefreshLayout.getHeight() * MAX_SWIPE_DISTANCE_FACTOR, refreshTriggerDistance * metrics.density);
        swipeRefreshLayout.setDistanceToTriggerSync(mDistanceToTriggerSync.intValue());
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                refresh();
            }
        });

        root=view.findViewById(R.id.root);
root.setOnClickListener(new View.OnClickListener() {
    @Override
    public void onClick(View view) {
        Intent intent = new Intent(getActivity(), AddStoryActivity.class);
        getActivity().startActivity(intent);
    }
});
        emptyView = view.findViewById(R.id.empty_view_container);
        empty_view_text = view.findViewById(R.id.empty_view_text);
        linearLayoutManager = new LinearLayoutManager(getContext());
        rv_food= view.findViewById(R.id.rv_food);
        foodlist = new ArrayList<>();
        category = new ArrayList<>();
        postModels = new ArrayList<>();
        userStoryArrayList = new ArrayList<>();
        rv_category=view.findViewById(R.id.rv_category);
        rv_category.setHasFixedSize(false);
        rv_category.setNestedScrollingEnabled(true);
        rv_category.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.HORIZONTAL, false));
        //  ItemOffsetDecoration itemDecoration = new ItemOffsetDecoration(requireActivity(), R.dimen.item_offset);
        //   recyclerViewMostVideo.addItemDecoration(itemDecoration);
        rv_food=view.findViewById(R.id.rv_food);
        rv_food.setHasFixedSize(false);
        rv_food.setNestedScrollingEnabled(true);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity()) {

            @Override
            public void smoothScrollToPosition(RecyclerView recyclerView, RecyclerView.State state, int position) {
                LinearSmoothScroller smoothScroller = new LinearSmoothScroller(getActivity()) {

                    private static final float SPEED = 300f;// Change this value (default=25f)

                    @Override
                    protected float calculateSpeedPerPixel(DisplayMetrics displayMetrics) {
                        return SPEED / displayMetrics.densityDpi;
                    }

                };
                smoothScroller.setTargetPosition(position);
                startSmoothScroll(smoothScroller);
            }

        };
        rv_food.setLayoutManager(layoutManager);
       // rv_food.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        retrofit = RetrofitClient.getClient(getResources().getString(R.string.base_url));
        service=retrofit.create(APIService.class);
       /* for (int i = 0; i < 5; i++) {
            ChildModel objItem = new ChildModel();
            objItem.setImage(image[i]);
            objItem.setName("Title "+i);
            objItem.setDescription("Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since the 1500s, when an unknown printer took a galley of type and scrambled it t");
            //objItem.setCategoryImageUrl(objJson2cat.getString(Constant.SUBCATEGORY_IMAGE));
            foodlist.add(objItem);
        }
        foodAdapter = new PostAdapter(getActivity(),foodlist);
        rv_food.setAdapter(foodAdapter);
  */    /*  for (int i = 0; i < categor.length; i++) {
            ChildModel objItem = new ChildModel();
            objItem.setImage(categor[i]);
            objItem.setName("Title "+i);
            //objItem.setCategoryImageUrl(objJson2cat.getString(Constant.SUBCATEGORY_IMAGE));
            category.add(objItem);
        }*/
        sharedPref=new SharedPref(getActivity());
        uid=sharedPref.getID();
        GetStory();
        GetPost();
        return view;
    }
    public  void GetStory()
    {

        Call<ArrayList<UserStory>> userCall = service.get_story(uid);
        userCall.enqueue(new Callback<ArrayList<UserStory>>() {
            @Override
            public void onResponse(Call<ArrayList<UserStory>> call, Response<ArrayList<UserStory>> response) {
              //  progressDialog.dismiss();
                try {

                    category=response.body();
                    Log.e("Leng","A"+category);
                    categoryAdapter = new CategoryAdapter(getActivity(),category,new CategoryAdapter.StoryClickListener(){
                        @Override
                        public void showStory(int pos) {
                            startActivity(StatusActivity.newIntent(getContext(), category, pos));
                        }
                    });

                    rv_category.setAdapter(categoryAdapter);
                   // Toast.makeText(AddActivity.this,"Post Added Successfully",Toast.LENGTH_LONG).show();


                    //    Toast.makeText(AddActivity.this,""+response.body().get(),Toast.LENGTH_LONG).show();

                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<ArrayList<UserStory>> call, Throwable t) {
                //progressDialog.dismiss();
               // Toast.makeText(AddActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }

    public  void GetPost()
    {
        if (swipeRefreshLayout.isRefreshing())
            swipeRefreshLayout.setRefreshing(false);

        Call<ArrayList<PostModel>> userCall = service.GetPost(uid);
        userCall.enqueue(new Callback<ArrayList<PostModel>>() {
            @Override
            public void onResponse(Call<ArrayList<PostModel>> call, Response<ArrayList<PostModel>> response) {
                //  progressDialog.dismiss();
                try {
                    postModels=response.body();
                    Log.e("Leng","A"+category.size());
                    foodAdapter = new PostAdapter(getActivity(),postModels);
                    rv_food.setAdapter(foodAdapter);   // Toast.makeText(AddActivity.this,"Post Added Successfully",Toast.LENGTH_LONG).show();
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmerAnimation();
                    //    Toast.makeText(AddActivity.this,""+response.body().get(),Toast.LENGTH_LONG).show();
                }catch (Exception ex)
                {
                }
            }
            @Override
            public void onFailure(Call<ArrayList<PostModel>> call, Throwable t) {
                //progressDialog.dismiss();
                // Toast.makeText(AddActivity.this,"Network Error",Toast.LENGTH_LONG).show();
                //hidepDialog();
                Log.d("onFailure", t.toString());
            }
        });
    }
}
