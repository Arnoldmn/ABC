package com.arnold.mna.abcinsurance.view;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.arnold.mna.abcinsurance.R;

public class PoliciesViewHoler extends RecyclerView.ViewHolder{

    View mView;

    public PoliciesViewHoler(View itemView) {
        super(itemView);
        mView = itemView;
    }

    public void setpolicy_number(String policyNumber){

        TextView policy_number = mView.findViewById(R.id.policyNumber);
        policy_number.setText(policyNumber);

    }

    public void setpolicy_property(String policyProperty){

        TextView policy_property = mView.findViewById(R.id.policiesDetails);
        policy_property.setText(policyProperty);


    }
}
