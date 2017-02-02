(function() {
    'use strict';
    angular
        .module('intelligentChartApp')
        .factory('MenuGroup', MenuGroup);

    MenuGroup.$inject = ['$resource'];

    function MenuGroup ($resource) {
        var resourceUrl =  'api/menu-groups/:id';

        return $resource(resourceUrl, {}, {
            'query': { method: 'GET', isArray: true},
            'get': {
                method: 'GET',
                transformResponse: function (data) {
                    if (data) {
                        data = angular.fromJson(data);
                    }
                    return data;
                }
            },
            'update': { method:'PUT' },
            'groupsWithMenus': {
                method: 'GET',
                url: 'api/menu-groups/with/menus',
                isArray: true
            }
        });
    }
})();
