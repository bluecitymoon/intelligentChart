(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonCreditCardActivityDialogController', PersonCreditCardActivityDialogController);

    PersonCreditCardActivityDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonCreditCardActivity', 'Person', 'CreditCardActivityType'];

    function PersonCreditCardActivityDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonCreditCardActivity, Person, CreditCardActivityType) {
        var vm = this;

        vm.personCreditCardActivity = entity;
        vm.clear = clear;
        vm.datePickerOpenStatus = {};
        vm.openCalendar = openCalendar;
        vm.save = save;
        vm.people = Person.query();
        vm.creditcardactivitytypes = CreditCardActivityType.query();

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personCreditCardActivity.id !== null) {
                PersonCreditCardActivity.update(vm.personCreditCardActivity, onSaveSuccess, onSaveError);
            } else {
                PersonCreditCardActivity.save(vm.personCreditCardActivity, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personCreditCardActivityUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }

        vm.datePickerOpenStatus.createDate = false;

        function openCalendar (date) {
            vm.datePickerOpenStatus[date] = true;
        }
    }
})();
