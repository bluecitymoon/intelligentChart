(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('PersonLawBusinessDeleteController',PersonLawBusinessDeleteController);

    PersonLawBusinessDeleteController.$inject = ['$uibModalInstance', 'entity', 'PersonLawBusiness'];

    function PersonLawBusinessDeleteController($uibModalInstance, entity, PersonLawBusiness) {
        var vm = this;

        vm.personLawBusiness = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            PersonLawBusiness.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
