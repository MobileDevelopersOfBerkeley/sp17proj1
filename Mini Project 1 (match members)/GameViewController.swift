//
//  GameViewController.swift
//  Mini Project 1 (match members)
//
//  Created by Boris Yue on 2/10/17.
//  Copyright © 2017 Boris Yue. All rights reserved.
//

import UIKit

class GameViewController: UIViewController {
    var members: [String] = ["Jessica Cherny", "Kevin Jiang", "Jared Gutierrez", "Kristin Ho", "Christine Munar", "Mudit Mittal", "Richard Hu", "Shaan Appel", "Edward Liu", "Wilbur Shi", "Young Lin", "Abhinav Koppu", "Abhishek Mangla", "Akkshay Khoslaa", "Andy Wang", "Aneesh Jindal", "Anisha Salunkhe", "Ashwin Vaidyanathan", "Cody Hsieh", "Justin Kim", "Krishnan Rajiyah", "Lisa Lee", "Peter Schafhalter", "Sahil Lamba", "Sirjan Kafle", "Tarun Khasnavis", "Billy Lu", "Aayush Tyagi", "Ben Goldberg", "Candice Ye", "Eliot Han", "Emaan Hariri", "Jessica Chen", "Katharine Jiang", "Kedar Thakkar", "Leon Kwak", "Mohit Katyal", "Rochelle Shen", "Sayan Paul", "Sharie Wang", "Shreya Reddy", "Shubham Goenka", "Victor Sun", "Vidya Ravikumar"];
    
    var pickedMembers: [String] = [] //members that have been picked already
    var pickedRandomNums: [Int] = [] //indexes that have been picked (used to ensure no repetition)
    var randNumButtonOne: Int?
    var randNumButtonTwo: Int?
    var randNumButtonThree: Int?
    var randNumButtonFour: Int?
    var correctIndex: Int?
    
    @IBOutlet weak var buttonOne: UIButton!
    @IBOutlet weak var buttonTwo: UIButton!
    @IBOutlet weak var buttonThree: UIButton!
    @IBOutlet weak var buttonFour: UIButton!
    @IBOutlet weak var randImage: UIImageView!
    
    
    @IBAction func buttonOneClicked(_ sender: Any) {
        checkCorrectAnswer(text: buttonOne.titleLabel!.text!)
    }
    @IBAction func buttonTwoClicked(_ sender: Any) {
        checkCorrectAnswer(text: buttonTwo.titleLabel!.text!)
    }
    @IBAction func buttonThreeClicked(_ sender: Any) {
        checkCorrectAnswer(text: buttonThree.titleLabel!.text!)
    }
    @IBAction func buttonFourClicked(_ sender: Any) {
        checkCorrectAnswer(text: buttonFour.titleLabel!.text!)
    }
    
    func checkCorrectAnswer(text: String) {
        if text == members[correctIndex!] {
            print("success")
        } else {
            print("fail")
        }
        members.remove(at: correctIndex!) //same picture won't show up again
    }
    override func viewDidLoad() {
        super.viewDidLoad()
        fillButtonsAndImage()
        
    }
    
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    
    
    func fillButtonsAndImage() {
        randNumButtonOne = generateRandomNum()
        randNumButtonTwo = generateRandomNum()
        randNumButtonThree = generateRandomNum()
        randNumButtonFour = generateRandomNum()
        pickedRandomNums.removeAll() //clear all indexes from array
        correctIndex = pickCorrectIndex(num1: randNumButtonOne!, num2: randNumButtonTwo!, num3: randNumButtonThree!, num4: randNumButtonFour!)
        loadCorrectImage(name: members[correctIndex!]) //load the image corresponding to correct name
        buttonOne.setTitle(members[randNumButtonOne!], for: .normal)
        buttonTwo.setTitle(members[randNumButtonTwo!], for: .normal)
        buttonThree.setTitle(members[randNumButtonThree!], for: .normal)
        buttonFour.setTitle(members[randNumButtonFour!], for: .normal)
    }
    
    func loadCorrectImage(name: String) {
        let editedString = name.replacingOccurrences(of: " ", with: "").lowercased()
        //lowercase and get rid of whitespace
        randImage.image = UIImage(named: editedString)
    }
    //generates random number that guarantees no repeats
    func generateRandomNum() -> Int {
        var randNum = Int(arc4random_uniform(UInt32(members.count)))
        while pickedRandomNums.contains(randNum) {
            randNum = Int(arc4random_uniform(UInt32(members.count)))
        }
        pickedRandomNums.append(randNum)
        return randNum
    }
    func pickCorrectIndex(num1: Int, num2: Int, num3: Int, num4: Int) -> Int {
        let randNum = Int(arc4random_uniform(UInt32(4)))
        switch randNum {
        case 0:
            return num1
        case 1:
            return num2
        case 2:
            return num3
        default:
            return num4
        }
    }
}
