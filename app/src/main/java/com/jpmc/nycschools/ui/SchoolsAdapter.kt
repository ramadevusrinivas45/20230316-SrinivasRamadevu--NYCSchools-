package com.jpmc.nycschools.ui

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.jpmc.nycschools.R
import com.jpmc.nycschools.data.SchoolModel
import com.jpmc.nycschools.databinding.AdapterSchoolsBinding
import javax.inject.Inject


class SchoolsAdapter @Inject constructor() : RecyclerView.Adapter<SchoolsAdapter.AcronymViewHolder>() {

    private var schools = mutableListOf<SchoolModel>()
    private var clickInterface: ClickInterface<SchoolModel>? = null

    fun updateSchools(schoolModelList: List<SchoolModel>) {
        notifyItemRangeRemoved(0, itemCount);
        this.schools = schoolModelList.toMutableList()
        notifyItemRangeInserted(0, schoolModelList.size)
    }


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AcronymViewHolder {
        val binding =
            AdapterSchoolsBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return AcronymViewHolder(binding)
    }

    override fun onBindViewHolder(holder: AcronymViewHolder, position: Int) {
        val school = schools[position]
        holder.view.tvTitle.text = school.school_name
        holder.view.tvLocation.text = holder.itemView.context.getString(R.string.location, school.location)
        holder.view.tvDescription.text = school.overview_paragraph
        holder.view.schoolCard.setOnClickListener {
            clickInterface?.onClick(school)
        }
    }

    override fun getItemCount(): Int {
        return schools.size
    }

    fun setItemClick(clickInterface: ClickInterface<SchoolModel>) {
        this.clickInterface = clickInterface
    }

    class AcronymViewHolder(val view: AdapterSchoolsBinding) : RecyclerView.ViewHolder(view.root)
}

interface ClickInterface<T> {
    fun onClick(data: T)
}