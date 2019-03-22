package com.ankuran.model.dao;

import com.ankuran.model.DueDetail;

import lombok.Data;

@Data
public class GroupWage {
//    {
//        "timeCreated": "2019-03-29T00:21:45.387Z",
//            "type": "DUE",
//            "dueDetails": {
//        "distributionType": "GROUP",
//                "item": {
//            "id": 136,
//                    "name": "Coach School Bag"
//        },
//        "quantity": 15,
//                "duePerItem": 120,
//                "amount": 1800,
//                "distribution": [
//        {
//            "employee": {
//            "id": 1
//        },
//            "amount": 500
//        },
//        {
//            "employee": {
//            "id": 9
//        },
//            "amount": 600
//        },
//        {
//            "employee": {
//            "id": 19
//        },
//            "amount": 700
//        }
//		]
//    },
//        "paymentDetails": null
//    }
//
//    timeCreated
//    type
//    DueDetail
//
//    {
//        distributionType
//                Item
//        quantity = 1
//        duePerItem = totalAmount
//        amount =
//                DistributionEmployee[]
//        {
//            Employee
//                    amount
//        }
//    }

    public String timeCreated;
    public String type;
    public DueDetail dueDetails;
}
