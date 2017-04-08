//
//  ViewController.swift
//  Mini Project 1 (match members)
//
//  Created by Boris Yue on 2/8/17.
//  Copyright © 2017 Boris Yue. All rights reserved.
//

import UIKit

class ViewController: UIViewController {

    var members: [String] = ["Jessica Cherny", "Kevin Jiang", "Jared Gutierrez", "Kristin Ho", "Christine Munar", "Mudit Mittal", "Richard Hu", "Shaan Appel", "Edward Liu", "Wilbur Shi", "Young Lin", "Abhinav Koppu", "Abhishek Mangla", "Akkshay Khoslaa", "Andy Wang", "Aneesh Jindal", "Anisha Salunkhe", "Ashwin Vaidyanathan", "Cody Hsieh", "Justin Kim", "Krishnan Rajiyah", "Lisa Lee", "Peter Schafhalter", "Sahil Lamba", "Sirjan Kafle", "Tarun Khasnavis", "Billy Lu", "Aayush Tyagi", "Ben Goldberg", "Candice Ye", "Eliot Han", "Emaan Hariri", "Jessica Chen", "Katharine Jiang", "Kedar Thakkar", "Leon Kwak", "Mohit Katyal", "Rochelle Shen", "Sayan Paul", "Sharie Wang", "Shreya Reddy", "Shubham Goenka", "Victor Sun", "Vidya Ravikumar"];
    
    var pickedRandomNums: [Int] = [] //indexes that have been picked (used to ensure no repetition)
    var pickedCorrectIndexes: [Int] = [] //correct indexes that have been picked (used to ensure no repetition)
    var lastThree: [String] = []
    var randNumButtonOne: Int?
    var randNumButtonTwo: Int?
    var randNumButtonThree: Int?
    var randNumButtonFour: Int?
    var correctIndex: Int?
    var score: Int = 0 {
        didSet {
            scoreLabel.title = "\(score)" + "/" + "\(members.count)"
        }
    }
    var timer = 5 {
        didSet {
            timerLabel.text = "\(timer)"
        }
    }
    var oneSecDelay = Timer()
    var fiveSecTimer = Timer()
    var longestStreak = 0
    var streakTrack = 0
    var firstTimeLoading = true
    
    @IBOutlet weak var buttonOne: UIButton!
    @IBOutlet weak var buttonTwo: UIButton!
    @IBOutlet weak var buttonThree: UIButton!
    @IBOutlet weak var buttonFour: UIButton!
    @IBOutlet weak var randImage: UIImageView!
    @IBOutlet weak var statButton: UIButton!
    @IBOutlet weak var scoreLabel: UIBarButtonItem!
    @IBOutlet weak var timerLabel: UILabel!
    @IBOutlet weak var startLabel: UIButton!
    @IBOutlet weak var titleLabel: UILabel!
    @IBOutlet weak var titlePic: UIImageView!
    
    override func viewDidLoad() {
        super.viewDidLoad()
        hideButtons()
        navigationController?.navigationBar.barTintColor = UIColor.lightGray
    }
    override func viewDidAppear(_ animated: Bool) {
        if firstTimeLoading == false {
            fiveSecTimer.invalidate()
            fiveSecTimer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(countDown), userInfo: nil, repeats: true)
        }
    
    }
    override func didReceiveMemoryWarning() {
        super.didReceiveMemoryWarning()
        // Dispose of any resources that can be recreated.
    }
    @IBAction func startButtonClicked(_ sender: Any) {
        startLabel.setTitle(startLabel.titleLabel?.text == "Stop" ? "Start" : "Stop", for: .normal)
        titleLabel.text = ""
        titlePic.isHidden = true
        if startLabel.titleLabel?.text == "Stop" {
            resetGame()
        } else {
            showButtons()
            fillButtonsAndImage()
        }
    }
    func resetGame() {
        fiveSecTimer.invalidate()
        score = 0
        hideButtons()
        randImage.isHidden = true;
        pickedCorrectIndexes.removeAll()
        lastThree = []
        longestStreak = 0
        streakTrack = 0
        timerLabel.text = "0"
        
    }
    func hideButtons() {
        buttonOne.isHidden = true
        buttonTwo.isHidden = true
        buttonThree.isHidden = true
        buttonFour.isHidden = true
    }
    func showButtons() {
        buttonOne.isHidden = false
        buttonTwo.isHidden = false
        buttonThree.isHidden = false
        buttonFour.isHidden = false
        buttonOne.layer.cornerRadius = 6
        buttonTwo.layer.cornerRadius = 6
        buttonThree.layer.cornerRadius = 6
        buttonFour.layer.cornerRadius = 6
        randImage.isHidden = false
    }

    func countDown() {
        timer -= 1;
        if timer == 0 {
            fiveSecTimer.invalidate()
            timer = 5
            fillButtonsAndImage()
        }
    }
    @IBAction func buttonOneClicked(_ sender: Any) {
        checkCorrectAnswer(button: buttonOne, text: buttonOne.titleLabel!.text!)
    }
    @IBAction func buttonTwoClicked(_ sender: Any) {
        checkCorrectAnswer(button: buttonTwo, text: buttonTwo.titleLabel!.text!)
    }
    @IBAction func buttonThreeClicked(_ sender: Any) {
        checkCorrectAnswer(button: buttonThree, text: buttonThree.titleLabel!.text!)
    }
    @IBAction func buttonFourClicked(_ sender: Any) {
        checkCorrectAnswer(button: buttonFour, text: buttonFour.titleLabel!.text!)
    }
    
    func checkCorrectAnswer(button: UIButton, text: String) {
        addToLastThree(text: (button.titleLabel?.text)!)
        let originalColor = button.backgroundColor
        if text == members[correctIndex!] {
            score += 1
            streakTrack += 1
            if streakTrack > longestStreak {
                longestStreak = streakTrack
            }
            UIView.animate(withDuration: 1, animations: { () -> Void in
                button.backgroundColor = UIColor.green;
            }, completion: { finished in
                button.backgroundColor = originalColor; //set to original color
            })
        } else {
            UIView.animate(withDuration: 1, animations: { () -> Void in
                button.backgroundColor = UIColor.red;
            }, completion: { finished in
                button.backgroundColor = originalColor;
            })
            streakTrack = 0
        }
        fiveSecTimer.invalidate()
        timer = 5
//        members.remove(at: correctIndex!) //same picture won't show up again
        oneSecDelay.invalidate()
        oneSecDelay = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(fillButtonsAndImage), userInfo: nil, repeats: false)
    }
    func addToLastThree(text: String) {
        if lastThree.count >= 3 {
            lastThree.remove(at: 0)
        }
        lastThree.append(text)
    }
    

    func fillButtonsAndImage() {
        fiveSecTimer.invalidate()
        fiveSecTimer = Timer.scheduledTimer(timeInterval: 1, target: self, selector: #selector(countDown), userInfo: nil, repeats: true) //reset timer
        randNumButtonOne = generateRandomNum()
        randNumButtonTwo = generateRandomNum()
        randNumButtonThree = generateRandomNum()
        randNumButtonFour = generateRandomNum()
        pickedRandomNums.removeAll() //clear all indexes from array
        correctIndex = pickCorrectIndex(num1: randNumButtonOne!, num2: randNumButtonTwo!, num3: randNumButtonThree!, num4: randNumButtonFour!)
        pickedCorrectIndexes.append(correctIndex!)
        loadCorrectImage(name: members[correctIndex!]) //load the image corresponding to correct name
        buttonOne.setTitle(members[randNumButtonOne!], for: .normal)
        buttonTwo.setTitle(members[randNumButtonTwo!], for: .normal)
        buttonThree.setTitle(members[randNumButtonThree!], for: .normal)
        buttonFour.setTitle(members[randNumButtonFour!], for: .normal)
    }
    
    func loadCorrectImage(name: String) {
        let editedString = name.replacingOccurrences(of: " ", with: "").lowercased()
        //lowercase and get rid of whitespace
        randImage.alpha = 0.0
        randImage.image = UIImage(named: editedString)
        randImage.layer.cornerRadius = 6
        randImage.clipsToBounds = true
        UIView.animate(withDuration: 0.75, animations: { () -> Void in
           self.randImage.alpha = 1.0 //image fade in animation
        })
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
        var randNum = Int(arc4random_uniform(UInt32(4)))
        let randNumConstant = randNum
        let nums = [num1, num2, num3, num4]
        while pickedCorrectIndexes.contains(nums[randNum]) {
            randNum = (randNum + 1) % 4
            if randNum == randNumConstant {
                //if all the random indexes have been selected as correct indexes before, regenerate numbers
                fillButtonsAndImage()
            }
        }
        return nums[randNum]
    }
    
    override func prepare(for segue: UIStoryboardSegue, sender: Any?) {
        if segue.identifier == "toStat" {
            let vc = segue.destination as! StatisticsViewController
            vc.longestStreak = String(longestStreak)
            vc.lastThree = lastThree
            fiveSecTimer.invalidate()
            firstTimeLoading = false
        }
    }

}

