const progress = (value) => {
    document.getElementsByClassName('progress-bar')[0].style.width = `${value}%`;
}

let step = document.getElementsByClassName('step');
let prevBtn = document.getElementById('prev-btn');
let nextBtn = document.getElementById('next-btn');

let submitBtn = document.getElementById('submit-btn');
let form = document.getElementsByTagName('form')[0];
let preloader = document.getElementById('preloader-wrapper');
let bodyElement = document.querySelector('body');
let successDiv = document.getElementById('success');

form.onsubmit = () => {
    return false
}

let current_step = 0;
let stepCount = 2
step[current_step].classList.add('d-block');
if (current_step == 0) {
    prevBtn.classList.add('d-none');
    submitBtn.classList.add('d-none');
    nextBtn.classList.add('d-inline-block');
}


nextBtn.addEventListener('click', () => {
    current_step++;
    let previous_step = current_step - 1;
    if ((current_step > 0) && (current_step <= stepCount)) {
        prevBtn.classList.remove('d-none');
        prevBtn.classList.add('d-inline-block');
        step[current_step].classList.remove('d-none');
        step[current_step].classList.add('d-block');
        step[previous_step].classList.remove('d-block');
        step[previous_step].classList.add('d-none');
        if (current_step == stepCount) {
            submitBtn.classList.remove('d-none');
            submitBtn.classList.add('d-inline-block');
            nextBtn.classList.remove('d-inline-block');
            nextBtn.classList.add('d-none');
        }

        if (current_step == 1) {
            retrieveFood(document.getElementById("food").value);
        }
    } else {
        if (current_step > stepCount) {
            form.onsubmit = () => {
                return true
            }
        }
    }
    progress((100 / stepCount) * current_step);
});


prevBtn.addEventListener('click', () => {
    if (current_step > 0) {
        current_step--;
        let previous_step = current_step + 1;
        prevBtn.classList.add('d-none');
        prevBtn.classList.add('d-inline-block');
        step[current_step].classList.remove('d-none');
        step[current_step].classList.add('d-block')
        step[previous_step].classList.remove('d-block');
        step[previous_step].classList.add('d-none');
        if (current_step < stepCount) {
            submitBtn.classList.remove('d-inline-block');
            submitBtn.classList.add('d-none');
            nextBtn.classList.remove('d-none');
            nextBtn.classList.add('d-inline-block');
            prevBtn.classList.remove('d-none');
            prevBtn.classList.add('d-inline-block');
        }
    }

    if (current_step == 0) {
        prevBtn.classList.remove('d-inline-block');
        prevBtn.classList.add('d-none');
    }
    progress((100 / stepCount) * current_step);
});


submitBtn.addEventListener('click', (e) => {
    preloader.classList.add('d-block');
    e.preventDefault();


    let recipe = $("input:checked")[0].value;
    let email = $("#email").val();
    console.log("Selected recipe " + recipe + " for " + email);


    $.post("/recipe/" + recipe + "?email=" + email)
        .done(c => {
            console.log(c);
            bodyElement.classList.add('loaded');
            step[stepCount].classList.remove('d-block');
            step[stepCount].classList.add('d-none');
            prevBtn.classList.remove('d-inline-block');
            prevBtn.classList.add('d-none');
            submitBtn.classList.remove('d-inline-block');
            submitBtn.classList.add('d-none');
            successDiv.classList.remove('d-none');
            successDiv.classList.add('d-block');
        });
});


function retrieveFood(tag) {
    $.getJSON("/recipe/tag/" + tag,
        function (c) {
            let meals = $("#meals");
            meals.empty();
            c.forEach(recipe => {
                let outerDiv = $("<div class='form-check ps-0 q-box'>")
                let innerDiv = $("<div class='box_form'>");
                let input = $("<input>").addClass("form-check-input form-input").attr("id", recipe.id)
                    .attr("name", recipe.id).attr("type", "checkbox").attr("value", recipe.id);
                let label = $("<label>").addClass("form-check-label form-label").attr("for", recipe.id);
                let img = $("<img>").addClass("rounded meal-picture")
                    .attr("src", recipe.image);
                let title = $("<span>" + recipe.title + "</span>").addClass("meal-title");
                label.append(img);
                label.append(title);
                innerDiv.append(input);
                innerDiv.append(label);
                outerDiv.append(innerDiv);
                meals.append(outerDiv);
            })


           /*
            <div class="form-check ps-0 q-box">
                            <div class="q-box_form">
                                <input class="form-check-input question__input" id="meal-1" name="meal-1" type="checkbox" value="Meal1">
                                <label class="form-check-label question__label" for="meal-1">
                                    <img src="https://spoonacular.com/recipeImages/660382-556x370.jpg" class="rounded meal-picture">
                                    meal1
                                </label>
                            </div>
                        </div>
            */
        })
}
