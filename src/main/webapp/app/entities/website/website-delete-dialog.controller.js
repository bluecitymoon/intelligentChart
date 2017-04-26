(function() {
    'use strict';

    angular
        .module('intelligentChartApp')
        .controller('WebsiteDeleteController',WebsiteDeleteController);

    WebsiteDeleteController.$inject = ['$uibModalInstance', 'entity', 'Website'];

    function WebsiteDeleteController($uibModalInstance, entity, Website) {
        var vm = this;

        vm.website = entity;
        vm.clear = clear;
        vm.confirmDelete = confirmDelete;

        function clear () {
            $uibModalInstance.dismiss('cancel');
        }

        function confirmDelete (id) {
            Website.delete({id: id},
                function () {
                    $uibModalInstance.close(true);
                });
        }
    }
})();
