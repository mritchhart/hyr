'use strict';

describe('Controller Tests', function() {

    describe('Student_social_skill Management Detail Controller', function() {
        var $scope, $rootScope;
        var MockEntity, MockStudent_social_skill, MockSocial_skill, MockStudent;
        var createController;

        beforeEach(inject(function($injector) {
            $rootScope = $injector.get('$rootScope');
            $scope = $rootScope.$new();
            MockEntity = jasmine.createSpy('MockEntity');
            MockStudent_social_skill = jasmine.createSpy('MockStudent_social_skill');
            MockSocial_skill = jasmine.createSpy('MockSocial_skill');
            MockStudent = jasmine.createSpy('MockStudent');
            

            var locals = {
                '$scope': $scope,
                '$rootScope': $rootScope,
                'entity': MockEntity ,
                'Student_social_skill': MockStudent_social_skill,
                'Social_skill': MockSocial_skill,
                'Student': MockStudent
            };
            createController = function() {
                $injector.get('$controller')("Student_social_skillDetailController", locals);
            };
        }));


        describe('Root Scope Listening', function() {
            it('Unregisters root scope listener upon scope destruction', function() {
                var eventType = 'hopeRanchLearningAcademyApp:student_social_skillUpdate';

                createController();
                expect($rootScope.$$listenerCount[eventType]).toEqual(1);

                $scope.$destroy();
                expect($rootScope.$$listenerCount[eventType]).toBeUndefined();
            });
        });
    });

});
