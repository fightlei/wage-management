package com.example.salary_management;

import java.util.List;

import android.content.Context;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseExpandableListAdapter;
import android.widget.TextView;

public class ContactsInfoAdapter extends BaseExpandableListAdapter {
	
	private Context context;
	private List<String> group;           //×éÁÐ±í  
    private List<List<String>> child;   
    
    

	public Context getContext() {
		return context;
	}

	public void setContext(Context context) {
		this.context = context;
	}

	public List<String> getGroup() {
		return group;
	}

	public void setGroup(List<String> group) {
		this.group = group;
	}

	public List<List<String>> getChild() {
		return child;
	}

	public void setChild(List<List<String>> child) {
		this.child = child;
	}

	@Override
	public int getGroupCount() {
		return group.size();  
	}

	@Override
	public int getChildrenCount(int groupPosition) {
		return child.get(groupPosition).size();  
	}

	@Override
	public Object getGroup(int groupPosition) {
		return group.get(groupPosition);  
	}

	@Override
	public Object getChild(int groupPosition, int childPosition) {
		return child.get(groupPosition).get(childPosition);
	}

	@Override
	public long getGroupId(int groupPosition) {
		return groupPosition;  
	}

	@Override
	public long getChildId(int groupPosition, int childPosition) {
		return childPosition;
	}

	@Override
	public boolean hasStableIds() {
		return false;
	}

	@Override
	public View getGroupView(int groupPosition, boolean isExpanded,
			View convertView, ViewGroup parent) {
		String string = group.get(groupPosition);    
        return getGenericView(string);  
	}

	@Override
	public View getChildView(int groupPosition, int childPosition,
			boolean isLastChild, View convertView, ViewGroup parent) {
		String string = child.get(groupPosition).get(childPosition);   
        return getGenericView(string);  
	}

	private TextView getGenericView(String string) {
		// Layout parameters for the ExpandableListView    
        AbsListView.LayoutParams lp = new AbsListView.LayoutParams(    
                ViewGroup.LayoutParams.FILL_PARENT, 150);  

        TextView text = new TextView(context);    
        text.setLayoutParams(lp);    
        // Center the text vertically    
        text.setGravity(Gravity.CENTER_VERTICAL | Gravity.LEFT);    
        // Set the text starting position    
        text.setPadding(130, 0, 0, 0);    
            
        text.setText(string);    
        return text;    
	}

	@Override
	public boolean isChildSelectable(int groupPosition, int childPosition) {
		return true;
	}

}
