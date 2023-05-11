package com.udacity.asteroidradar.main


import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.udacity.asteroidradar.Asteroid
import com.udacity.asteroidradar.databinding.AsteroidItemBinding

class AsteroidAdapter(val onClickListener: OnClickListener) : androidx.recyclerview.widget.ListAdapter<Asteroid, AsteroidAdapter.AsteroidViewHolder>(DiffCallBack) {
object DiffCallBack : DiffUtil.ItemCallback<Asteroid>(){
    override fun areItemsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem === newItem
    }

    override fun areContentsTheSame(oldItem: Asteroid, newItem: Asteroid): Boolean {
        return oldItem.id == newItem.id
    }

}
    class AsteroidViewHolder(val binding : AsteroidItemBinding) : RecyclerView.ViewHolder(binding.root){
        fun bind(asteroidProperty : Asteroid){
            binding.asteroid = asteroidProperty
            binding.executePendingBindings()

        }

    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): AsteroidViewHolder {
        return AsteroidViewHolder(AsteroidItemBinding.inflate(LayoutInflater.from(parent.context)))
    }

    override fun onBindViewHolder(holder: AsteroidViewHolder, position: Int) {
        val item = getItem(position)
        holder.bind(item)
        holder.itemView.setOnClickListener {
            onClickListener.onCLick(item)
        }
    }

    class OnClickListener(val clickListener : (asteroid : Asteroid) -> Unit){
        fun onCLick(asteroid: Asteroid) = clickListener(asteroid)
    }

}