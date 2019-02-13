package com.example.katie.hrubieckatherine_ce07.fragments;

import android.app.Fragment;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.katie.hrubieckatherine_ce07.R;
import com.example.katie.hrubieckatherine_ce07.TreasureItem;
import com.example.katie.hrubieckatherine_ce07.activities.MainActivity;

public class InventoryFragment extends Fragment {

    public static final String TAG = "InventoryFragment.TAG";

    public static InventoryFragment newInstance() {

        Bundle args = new Bundle();

        InventoryFragment fragment = new InventoryFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.inventory_fragment, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(getView() != null) {
            TextView tv = getView().findViewById(R.id.inventory);
            tv.setMovementMethod(new ScrollingMovementMethod());
            if (MainActivity.foundItems.size() != 0) {
                tv.setText("");
                int goldTotal = 0;
                int etherTotal = 0;
                int hiEtherTotal = 0;
                int hiPotionTotal = 0;
                int potionTotal = 0;
                int rustySwordTotal = 0;
                int xPotionTotal = 0;

                for (TreasureItem ti : MainActivity.foundItems) {
                    switch (ti.getName()) {
                        case "Gold":
                            goldTotal += ti.getWorth();
                            break;
                        case "Ether":
                            etherTotal++;
                            break;
                        case "Hi-Ether":
                            hiEtherTotal++;
                            break;
                        case "Hi-Potion":
                            hiPotionTotal++;
                            break;
                        case "Potion":
                            potionTotal++;
                            break;
                        case "Rusty Sword":
                            rustySwordTotal++;
                            break;
                        case "X-Potion":
                            xPotionTotal++;
                            break;
                        default:
                            tv.append(ti.getName() + "\n");
                            break;
                    }
                }
                tv.append("Gold $" + goldTotal + "\n");
                tv.append("Ethers - " + etherTotal + "\n");
                tv.append("Hi-Ethers - " + hiEtherTotal + "\n");
                tv.append("Hi-Potions - " + hiPotionTotal + "\n");
                tv.append("Potions - " + potionTotal + "\n");
                tv.append("Rusty Swords - " + rustySwordTotal + "\n");
                tv.append("X-Potions - " + xPotionTotal + "\n");
            }
        }
    }

}
