package ${packageName}.view;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.content.Context;
import android.content.Intent;

import ${packageName}.contract.${contractClass};
import ${packageName}.presenter.${presenterClass};

public class ${fragmentClass} extends Fragment implements ${contractClass}.View {

    private ${contractClass}.Presenter presenter;

    public static ${fragmentClass} newInstance() {
        ${fragmentClass} fragment = new ${fragmentClass}();
        return fragment;
    }

     @Nullable
     @Override
     public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
         View view = inflater.inflate(R.layout.${layoutName}, container, false);
         return view;
     }

     @Override
     public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
         super.onViewCreated(view, savedInstanceState);
         initView();
         initData();
     }

    private void initView(){

    }

    private void initData(){
        presenter = new ${presenterClass}(this);
    }

    @Override
    protected void onDestroyView() {
        presenter.destroy();
        super.onDestroy();
    }

}
