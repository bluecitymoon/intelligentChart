(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonIncomeDialogController', PersonIncomeDialogController);

    PersonIncomeDialogController.$inject = ['$timeout', '$scope', '$stateParams', '$uibModalInstance', 'entity', 'PersonIncome', 'Person', '$rootScope'];

    function PersonIncomeDialogController ($timeout, $scope, $stateParams, $uibModalInstance, entity, PersonIncome, Person, $rootScope) {
        var vm = this;

        vm.personIncome = entity;
        vm.personIncome.inSalaryTotal = vm.personIncome.inCountryPlusBoxTotal - vm.personIncome.inCountrySalaryTotal;
        vm.personIncome.outSalaryTotal = vm.personIncome.outCountryPlusBoxTotal - vm.personIncome.outCountrySalaryTotal;
        vm.clear = clear;
        vm.save = save;
        vm.people = Person.query({size: DEFAULT_HELPER_DATA_SOURCE_SIZE});

        vm.calculateTotalInCountry = calculateTotalInCountry;
        vm.calculateTotalOutCountry = calculateTotalOutCountry;

        function calculateTotalOutCountry() {
            vm.personIncome.outCountryPlusBoxTotal = vm.personIncome.outCountrySalaryTotal +  vm.personIncome.outSalaryTotal;
        }

        function calculateTotalInCountry() {
            vm.personIncome.inCountryPlusBoxTotal = vm.personIncome.inCountrySalaryTotal +  vm.personIncome.inSalaryTotal;
        }

        $timeout(function (){
            angular.element('.form-group:eq(1)>input').focus();
        });

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function save () {
            vm.isSaving = true;
            if (vm.personIncome.id !== null) {
                PersonIncome.update(vm.personIncome, onSaveSuccess, onSaveError);
            } else {
                PersonIncome.save(vm.personIncome, onSaveSuccess, onSaveError);
            }
        }

        function onSaveSuccess (result) {
            $scope.$emit('intelligentChartApp:personIncomeUpdate', result);
            $uibModalInstance.close(result);
            vm.isSaving = false;
        }

        function onSaveError () {
            vm.isSaving = false;
        }


    }
})();
